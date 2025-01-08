package com.AppTransporte.AppTransportePublico.Service;
import com.AppTransporte.AppTransportePublico.Dto.Response.DtoVehicle;
import com.AppTransporte.AppTransportePublico.Dto.ResponseGeneral;
import com.AppTransporte.AppTransportePublico.Dto.Requets.RequestVehicle;
import com.AppTransporte.AppTransportePublico.Entity.TUser;
import com.AppTransporte.AppTransportePublico.Entity.TVehicle;
import com.AppTransporte.AppTransportePublico.Repository.RepoUser;
import com.AppTransporte.AppTransportePublico.Repository.RepoVehicle;
import com.AppTransporte.AppTransportePublico.Service.GenericService.GenericConverter;
import com.AppTransporte.AppTransportePublico.Bussines.VehicleBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VehicleService {

    @Autowired
    private RepoVehicle repoVehicle;

    @Autowired
    private RepoUser repoUser;

    @Autowired
    private GenericConverter<RequestVehicle, DtoVehicle, TVehicle> genericConverter;

    @Autowired
    private VehicleBusiness vehicleBusiness;

    public ResponseGeneral insertVehicle(RequestVehicle requestVehicle) {
        ResponseGeneral response = new ResponseGeneral();
        List<String> errors = new ArrayList<>();
    
        try {
            // Validar placa única
            vehicleBusiness.validateLicensePlate(requestVehicle.getLicensePlate(), null, errors);
    
            // Validar usuario existente
            Optional<TUser> user = repoUser.findById(requestVehicle.getIdUser());
            if (user.isEmpty()) {
                errors.add("El usuario asociado no existe.");
            }
    
            if (!errors.isEmpty()) {
                response.error(errors);
                response.setMessage("Error al insertar el vehículo.");
                return response;
            }
    
            // Convertir a entidad y guardar
            DtoVehicle dtoVehicle = genericConverter.toDTO(requestVehicle);
            TVehicle vehicle = genericConverter.toEntity(dtoVehicle);
            vehicle.setIdVehicle(UUID.randomUUID().toString());
            vehicle.setUser(user.get());
            repoVehicle.save(vehicle);
    
            response.success("Vehículo registrado exitosamente.");
            return response;
    
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error interno en el servicio: " + e.getMessage());
        }
    }
    
    public List<DtoVehicle> listAllVehicles() {
        List<TVehicle> vehicles = repoVehicle.findAll();
    
        // Convertir las entidades a DTOs
        return vehicles.stream()
                .map(genericConverter::toDTOFromEntity)
                .toList();
    }
    

    public ResponseGeneral deleteVehicleById(String idVehicle) {
        ResponseGeneral response = new ResponseGeneral();
        Optional<TVehicle> vehicle = repoVehicle.findById(idVehicle);

        if (vehicle.isEmpty()) {
            response.error("El vehículo no existe.");
            response.setMessage("Error al eliminar el vehículo.");
            return response;
        }

        repoVehicle.delete(vehicle.get());
        response.success("Vehículo eliminado exitosamente.");
        return response;
    }

    public ResponseGeneral getVehicleById(String idVehicle) {
        ResponseGeneral response = new ResponseGeneral();
        Optional<TVehicle> vehicle = repoVehicle.findById(idVehicle);

        if (vehicle.isEmpty()) {
            response.error("El vehículo no existe.");
            response.setMessage("Error al buscar el vehículo.");
            return response;
        }

        DtoVehicle dtoVehicle = genericConverter.toDTOFromEntity(vehicle.get());
        response.setData(dtoVehicle);
        response.success("Vehículo encontrado.");
        return response;
    }
}
