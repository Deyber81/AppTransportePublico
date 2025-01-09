package AppTansporte.AppTransportepublico.Services;
import AppTansporte.AppTransportepublico.Business.VehicleBusiness;
import AppTansporte.AppTransportepublico.Dto.ResponseGeneral;
import AppTansporte.AppTransportepublico.Dto.Response.DtoVehicle;
import AppTansporte.AppTransportepublico.Entity.TVehicle;
import AppTansporte.AppTransportepublico.Repository.RepoVehicle;
import AppTansporte.AppTransportepublico.Business.GenericMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    @Autowired
    private RepoVehicle repoVehicle;

    @Autowired
    private GenericMapper genericMapper;

    @Autowired
    private VehicleBusiness vehicleBusiness;
    public ResponseGeneral insertVehicle(DtoVehicle dtoVehicle) {
        ResponseGeneral response = new ResponseGeneral();
        List<String> errorMessages = new ArrayList<>();
    
        // Validar si el usuario existe
        vehicleBusiness.validateUserExistence(dtoVehicle.getIdUser(), errorMessages);
    
        // Validar si la placa es única
        vehicleBusiness.validateLicensePlateUniqueness(dtoVehicle.getLicensePlate(), errorMessages);
    
        if (!errorMessages.isEmpty()) {
            response.error(errorMessages);
            return response;
        }
        dtoVehicle.setIdVehicle(UUID.randomUUID().toString());
        dtoVehicle.setGPSDeviceId(UUID.randomUUID().toString());
        // Mapear DTO a entidad y guardar
        System.out.println("DTO antes de guardar: " + dtoVehicle);
        TVehicle vehicle = genericMapper.map(dtoVehicle, TVehicle.class);
        repoVehicle.save(vehicle);
    
        response.success("Vehículo guardado exitosamente.");
        return response;
    }
    

    public ResponseGeneral updateVehicle(DtoVehicle dtoVehicle) {
        ResponseGeneral response = new ResponseGeneral();
        List<String> errorMessages = new ArrayList<>();

        try {
            // Verificar si el vehículo existe
            Optional<TVehicle> optionalVehicle = repoVehicle.findById(dtoVehicle.getIdVehicle());
            if (optionalVehicle.isEmpty()) {
                response.error("El vehículo con el ID especificado no existe.");
                return response;
            }
            // Validar si el usuario existe
            vehicleBusiness.validateUserExistence(dtoVehicle.getIdUser(), errorMessages);
            TVehicle existingVehicle = optionalVehicle.get();

            // Validar unicidad de la placa si se actualiza
            if (dtoVehicle.getLicensePlate() != null 
                    && !existingVehicle.getLicensePlate().equals(dtoVehicle.getLicensePlate())) {
                vehicleBusiness.validateLicensePlateUniqueness(dtoVehicle.getLicensePlate(), errorMessages);
                if (!errorMessages.isEmpty()) {
                    response.error(errorMessages);
                    return response;
                }
                existingVehicle.setLicensePlate(dtoVehicle.getLicensePlate());
            }

            // Actualizar otros campos
            if (dtoVehicle.getModel() != null) {
                existingVehicle.setModel(dtoVehicle.getModel());
            }
            if (dtoVehicle.getCapacity() > 0) {
                existingVehicle.setCapacity(dtoVehicle.getCapacity());
            }
            if (dtoVehicle.getIdUser() != null) {
                existingVehicle.setIdUser(dtoVehicle.getIdUser());
            }

            // Guardar los cambios
            repoVehicle.save(existingVehicle);

            response.success("Vehículo actualizado exitosamente.");
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            response.error("Error interno del servidor: " + e.getMessage());
            return response;
        }
    }

    public List<DtoVehicle> listAllVehicles() {
        return repoVehicle.findAll()
                .stream()
                .map(vehicle -> genericMapper.map(vehicle, DtoVehicle.class))
                .collect(Collectors.toList());
    }

    public ResponseGeneral getVehicleById(String idVehicle) {
        ResponseGeneral response = new ResponseGeneral();

        Optional<TVehicle> vehicleOptional = repoVehicle.findById(idVehicle);
        if (vehicleOptional.isEmpty()) {
            response.error("Vehículo no encontrado.");
            return response;
        }

        DtoVehicle dtoVehicle = genericMapper.map(vehicleOptional.get(), DtoVehicle.class);

        response.success("Vehículo encontrado.");
        response.setData(dtoVehicle);
        return response;
    }

    public ResponseGeneral deleteVehicleById(String idVehicle) {
        ResponseGeneral response = new ResponseGeneral();

        if (!repoVehicle.existsById(idVehicle)) {
            response.error("Vehículo no encontrado.");
            return response;
        }

        repoVehicle.deleteById(idVehicle);

        response.success("Vehículo eliminado exitosamente.");
        return response;
    }
}
