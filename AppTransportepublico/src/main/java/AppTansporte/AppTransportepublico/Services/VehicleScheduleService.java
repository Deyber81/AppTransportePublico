package AppTansporte.AppTransportepublico.Services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AppTansporte.AppTransportepublico.Business.GenericMapper;
import AppTansporte.AppTransportepublico.Business.VehicleScheduleBusiness;
import AppTansporte.AppTransportepublico.Dto.ResponseGeneral;
import AppTansporte.AppTransportepublico.Dto.Response.DtoVehicleSchedule;
import AppTansporte.AppTransportepublico.Entity.TVehicleSchedule;
import AppTansporte.AppTransportepublico.Repository.RepoVehicleSchedule;

@Service
public class VehicleScheduleService {

    @Autowired
    private RepoVehicleSchedule vehicleScheduleRepository;

    @Autowired
    private VehicleScheduleBusiness vehicleScheduleBusiness;

    @Autowired
    private GenericMapper genericMapper;

    public ResponseGeneral insertVehicleSchedule(DtoVehicleSchedule dtoVehicleSchedule) {
    ResponseGeneral response = new ResponseGeneral();
        Set<String> uniqueErrorMessages = new HashSet<>(); // Usar un Set para mensajes únicos
        List<TVehicleSchedule> schedules = new ArrayList<>();

        // Generar automáticamente todos los horarios del día
        try {
            vehicleScheduleBusiness.generateFullDaySchedules(dtoVehicleSchedule, schedules);
        } catch (IllegalArgumentException e) {
            uniqueErrorMessages.add(e.getMessage());
        }

        // Validar los horarios generados
        for (TVehicleSchedule schedule : schedules) {
            DtoVehicleSchedule scheduleDto = genericMapper.map(schedule, DtoVehicleSchedule.class);
            vehicleScheduleBusiness.validateRouteScheduleTimes(scheduleDto, uniqueErrorMessages);
            vehicleScheduleBusiness.validateVehicleExists(scheduleDto.getIdVehicle(), uniqueErrorMessages);
            vehicleScheduleBusiness.validateVehicleSchedule(scheduleDto, uniqueErrorMessages);
        }

        // Verificar si hubo errores
        if (!uniqueErrorMessages.isEmpty()) {
            response.error(String.join(", ", uniqueErrorMessages)); // Unir mensajes en un solo string
            return response;
        }

        // Guardar los horarios generados
        vehicleScheduleRepository.saveAll(schedules);

        response.success("Todos los horarios generados y guardados exitosamente.");
        return response;
    }

    public ResponseGeneral editSchedules(DtoVehicleSchedule dtoSchedule) {
        // Llamar a la lógica de negocio para procesar la edición
        ResponseGeneral response = vehicleScheduleBusiness.editSchedulesByVehicleAndDay(
                dtoSchedule.getIdVehicle(),
                dtoSchedule.getDayOfWeek(),
                dtoSchedule.getIdVehicle(), // Nuevo ID de vehículo (si aplica)
                dtoSchedule.getIdRoute(),   // Nuevo ID de ruta (si aplica)
                dtoSchedule.getDepartureTime() // Nueva hora de salida (si aplica)
        );

        return response;
    }

    // Eliminar un horario
    public ResponseGeneral deleteVehicleSchedule(String idSchedule) {
        ResponseGeneral response = new ResponseGeneral();
        Set<String> uniqueErrorMessages = new HashSet<>();

        // Validar existencia del horario
        vehicleScheduleBusiness.validateScheduleExistence(idSchedule, uniqueErrorMessages);

        // Verificar si hubo errores
        if (!uniqueErrorMessages.isEmpty()) {
            response.error(String.join(", ", uniqueErrorMessages)); // Unir mensajes en un solo string
            return response;
        }

        // Eliminar el horario por ID
        vehicleScheduleRepository.deleteById(idSchedule);

        response.success("Horario eliminado exitosamente.");
        return response;
    }
    /**
     * Eliminar todos los horarios de un vehículo para un día específico.
     */
    public ResponseGeneral deleteSchedulesByVehicleAndDay(String idVehicle, String dayOfWeek) {
        // Call the business layer method to handle deletion
        return vehicleScheduleBusiness.deleteSchedulesByVehicleAndDay(idVehicle, dayOfWeek);
    }
    // Obtener horario por ID
    public ResponseGeneral getVehicleScheduleById(String idSchedule) {
        ResponseGeneral response = new ResponseGeneral();

        Optional<TVehicleSchedule> scheduleOptional = vehicleScheduleRepository.findById(idSchedule);
        if (scheduleOptional.isEmpty()) {
            response.error("El horario con el ID especificado no existe.");
            return response;
        }

        DtoVehicleSchedule dto = genericMapper.map(scheduleOptional.get(), DtoVehicleSchedule.class);
        response.success("Horario encontrado.");
        response.setData(dto);
        return response;
    }

    // Listar todos los horarios
    public List<DtoVehicleSchedule> listAllSchedules() {
        return vehicleScheduleRepository.findAll().stream()
                .map(schedule -> genericMapper.map(schedule, DtoVehicleSchedule.class))
                .collect(Collectors.toList());
    }

}