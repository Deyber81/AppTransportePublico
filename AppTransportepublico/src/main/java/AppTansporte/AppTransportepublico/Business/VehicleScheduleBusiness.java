package AppTansporte.AppTransportepublico.Business;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import AppTansporte.AppTransportepublico.Dto.ResponseGeneral;
import AppTansporte.AppTransportepublico.Dto.Response.DtoVehicleSchedule;
import AppTansporte.AppTransportepublico.Entity.TVehicleSchedule;
import AppTansporte.AppTransportepublico.Repository.RepoVehicle;
import AppTansporte.AppTransportepublico.Repository.RepoVehicleSchedule;

@Component
public class VehicleScheduleBusiness {

    @Autowired
    private RepoVehicleSchedule vehicleScheduleRepository;
    @Autowired
    private RepoVehicle vehicleRepository;

    // Validar existencia del horario
    public void validateScheduleExistence(String idSchedule, Set<String> errorMessages) {
        if (!vehicleScheduleRepository.existsById(idSchedule)) {
            errorMessages.add("El horario con el ID especificado no existe.");
        }
    }
    public void validateVehicleExists(String idVehicle, Set<String> errorMessages) {
        if (!vehicleRepository.existsById(idVehicle)) {
            errorMessages.add("El vehículo con ID  no existe.");
        }
    }
    // Actualizar los campos de un horario existente
    public void updateScheduleFields(DtoVehicleSchedule dto, TVehicleSchedule schedule) {
        // Actualizar campos básicos
        schedule.setDayOfWeek(dto.getDayOfWeek());
        schedule.setDepartureTime(dto.getDepartureTime());
        schedule.setArrivalTime(dto.getArrivalTime());

        // Actualizar ID de la ruta si cambia
        if (dto != null && (schedule.getIdRoute() == null || !schedule.getIdRoute().equals(dto.getIdRoute()))) {
            schedule.setIdRoute(dto.getIdRoute());
        }

        // Actualizar ID del vehículo si cambia
        if (dto.getIdVehicle() != null && (schedule.getIdVehicle() == null || !schedule.getIdVehicle().equals(dto.getIdVehicle()))) {
            schedule.setIdVehicle(dto.getIdVehicle());
        }
    }
    public void validateRouteScheduleTimes(DtoVehicleSchedule dto, Set<String> errorMessages ) {
        // Obtener las horas de inicio y fin de la ruta
        LocalTime routeStartTime = vehicleScheduleRepository.findRouteStartTimeById(dto.getIdRoute());
        LocalTime routeEndTime = vehicleScheduleRepository.findRouteEndTimeById(dto.getIdRoute());
    
        if (routeStartTime == null || routeEndTime == null) {
            errorMessages.add("No se encontraron las horas de inicio o fin de la ruta especificada: " + dto.getIdRoute());
            return;
        }
    
        // Validar que la hora de inicio del horario no sea anterior a la de la ruta
        if (dto.getDepartureTime().isBefore(routeStartTime)) {
            errorMessages.add("La hora de inicio del horario (" + dto.getDepartureTime() +
                    ") debe ser mayor o igual a la hora de inicio de la ruta: " + routeStartTime);
        }
    
        // Validar que la hora de fin del horario no sea posterior a la de la ruta
        if (dto.getArrivalTime().isAfter(routeEndTime)) {
            errorMessages.add("La hora de fin del horario (" + dto.getArrivalTime() +
                    ") debe ser menor o igual a la hora de fin de la ruta: " + routeEndTime);
        }
    }
   public void validateVehicleSchedule(DtoVehicleSchedule dto, Set<String> errorMessages) {
        // Usar un Set para evitar mensajes duplicados
        Set<String> uniqueErrorMessages = new HashSet<>();

        // Obtener horarios que coincidan con el vehículo y el día
        List<TVehicleSchedule> conflictingSchedules = vehicleScheduleRepository.findByVehicleAndDay(
                dto.getIdVehicle(), dto.getDayOfWeek(), dto.getDepartureTime(), dto.getArrivalTime());

        // Validar si hay conflictos en el mismo día
        if (!conflictingSchedules.isEmpty()) {
            uniqueErrorMessages.add("El vehículo ya está asignado a otra ruta en el horario especificado para el día: " 
                    + dto.getDayOfWeek());
        }

        // Agregar los mensajes únicos al errorMessages original
        errorMessages.addAll(uniqueErrorMessages);
    }
    public void calculateArrivalTime(DtoVehicleSchedule dto) {
        // Obtener la duración de la ruta en minutos desde la base de datos
        Integer routeDuration = vehicleScheduleRepository.findRouteDurationById(dto.getIdRoute());
    
        if (routeDuration == null) {
            throw new IllegalArgumentException("No se encontró la duración de la ruta con ID: " + dto.getIdRoute());
        }
    
        // Calcular la hora de llegada sumando la duración a la hora de salida
        LocalTime arrivalTime = dto.getDepartureTime().plusMinutes(routeDuration);
    
        // Establecer la hora de llegada en el DTO
        dto.setArrivalTime(arrivalTime);
    }
    public void generateFullDaySchedules(DtoVehicleSchedule dto, List<TVehicleSchedule> schedules) {
        // Obtener configuración de la ruta
        Integer routeDuration = vehicleScheduleRepository.findRouteDurationById(dto.getIdRoute());
        Integer breakInterval = vehicleScheduleRepository.findBreakIntervalById(dto.getIdRoute());
        LocalTime routeStartTime = vehicleScheduleRepository.findRouteStartTimeById(dto.getIdRoute());
        LocalTime routeEndTime = vehicleScheduleRepository.findRouteEndTimeById(dto.getIdRoute());
    
        if (routeDuration == null || breakInterval == null || routeStartTime == null || routeEndTime == null) {
            throw new IllegalArgumentException("Faltan datos de la ruta: duración, descansos o límites de tiempo.");
        }
    
        // Inicializar tiempos
        LocalTime currentDeparture = dto.getDepartureTime() != null ? dto.getDepartureTime() : routeStartTime;
    
        while (currentDeparture.isBefore(routeEndTime)) {
            // **Viaje de Ida**
            TVehicleSchedule idaSchedule = new TVehicleSchedule();
            idaSchedule.setIdSchedule(UUID.randomUUID().toString());
            idaSchedule.setIdVehicle(dto.getIdVehicle());
            idaSchedule.setIdRoute(dto.getIdRoute());
            idaSchedule.setDayOfWeek(dto.getDayOfWeek());
            idaSchedule.setDepartureTime(currentDeparture);
            idaSchedule.setArrivalTime(currentDeparture.plusMinutes(routeDuration));
            idaSchedule.setDirection("Ida");
    
            if (idaSchedule.getArrivalTime().isAfter(routeEndTime)) break; // Si excede el fin de operación
    
            schedules.add(idaSchedule);
    
            // **Descanso después del viaje de ida**
            LocalTime breakStartTime = idaSchedule.getArrivalTime();
            LocalTime breakEndTime = breakStartTime.plusMinutes(breakInterval);
    
            // **Viaje de Vuelta**
            TVehicleSchedule vueltaSchedule = new TVehicleSchedule();
            vueltaSchedule.setIdSchedule(UUID.randomUUID().toString());
            vueltaSchedule.setIdVehicle(dto.getIdVehicle());
            vueltaSchedule.setIdRoute(dto.getIdRoute());
            vueltaSchedule.setDayOfWeek(dto.getDayOfWeek());
            vueltaSchedule.setDepartureTime(breakEndTime);
            vueltaSchedule.setArrivalTime(breakEndTime.plusMinutes(routeDuration));
            vueltaSchedule.setDirection("Vuelta");
    
            if (vueltaSchedule.getArrivalTime().isAfter(routeEndTime)) break; // Si excede el fin de operación
    
            schedules.add(vueltaSchedule);
    
            // Actualizar el tiempo de salida para el próximo ciclo
            currentDeparture = vueltaSchedule.getArrivalTime().plusMinutes(breakInterval);
        }
    }
      // Validar y obtener horarios para eliminar
    public List<TVehicleSchedule> getSchedulesForDeletion(String idVehicle, String dayOfWeek, List<String> errorMessages) {
        List<TVehicleSchedule> schedules = vehicleScheduleRepository.findByVehicleAndDay(idVehicle, dayOfWeek);

        if (schedules.isEmpty()) {
            errorMessages.add("No se encontraron horarios para el vehículo en el día " + dayOfWeek + ".");
        }

        return schedules;
    }
    public ResponseGeneral deleteSchedulesByVehicleAndDay(String idVehicle, String dayOfWeek) {
        ResponseGeneral response = new ResponseGeneral();

        // Obtener los horarios del vehículo en el día especificado
        List<TVehicleSchedule> schedules = vehicleScheduleRepository.findByVehicleAndDay(idVehicle, dayOfWeek);

        if (schedules.isEmpty()) {
            response.error("No se encontraron horarios para el vehículo con ID " + idVehicle + " en el día: " + dayOfWeek);
            return response;
        }

        // Eliminar los horarios encontrados
        vehicleScheduleRepository.deleteAll(schedules);

        response.success(schedules.size() + " horarios eliminados exitosamente para el día: " + dayOfWeek);
        return response;
    }
    public ResponseGeneral editSchedulesByVehicleAndDay(String idVehicle, String dayOfWeek, LocalTime newDepartureTime) {
        ResponseGeneral response = new ResponseGeneral();

        // Obtener los horarios del vehículo en el día especificado
        List<TVehicleSchedule> schedules = vehicleScheduleRepository.findByVehicleAndDay(idVehicle, dayOfWeek);

        if (schedules.isEmpty()) {
            response.error("No se encontraron horarios para el vehículo con ID " + idVehicle + " en el día: " + dayOfWeek);
            return response;
        }

        // Calcular la diferencia de tiempo
        LocalTime originalDepartureTime = schedules.get(0).getDepartureTime();
        Duration timeDifference = Duration.between(originalDepartureTime, newDepartureTime);

        // Actualizar todos los horarios
        for (TVehicleSchedule schedule : schedules) {
            schedule.setDepartureTime(schedule.getDepartureTime().plus(timeDifference));
            schedule.setArrivalTime(schedule.getArrivalTime().plus(timeDifference));
        }

        // Guardar los horarios actualizados
        vehicleScheduleRepository.saveAll(schedules);

        response.success(schedules.size() + " horarios actualizados exitosamente para el día: " + dayOfWeek);
        return response;
    }
public ResponseGeneral editSchedulesByVehicleAndDay(String idVehicle, String dayOfWeek, String newVehicleId, String newRouteId, LocalTime newDepartureTime) {
        ResponseGeneral response = new ResponseGeneral();

        // Obtener los horarios relacionados
        List<TVehicleSchedule> schedules = vehicleScheduleRepository.findByVehicleAndDay(idVehicle, dayOfWeek);

        if (schedules.isEmpty()) {
            response.error("No se encontraron horarios para el vehículo con ID " + idVehicle + " en el día: " + dayOfWeek);
            return response;
        }

        // Obtener datos originales y calcular diferencias
        LocalTime originalDepartureTime = schedules.get(0).getDepartureTime();
        Duration timeDifference = Duration.between(originalDepartureTime, newDepartureTime);

        // Actualizar todos los horarios relacionados
        for (TVehicleSchedule schedule : schedules) {
            if (newVehicleId != null && !newVehicleId.equals(schedule.getIdVehicle())) {
                schedule.setIdVehicle(newVehicleId);
            }
            if (newRouteId != null && !newRouteId.equals(schedule.getIdRoute())) {
                schedule.setIdRoute(newRouteId);

                // Recalcular horas de llegada basadas en la nueva ruta
                Integer routeDuration = vehicleScheduleRepository.findRouteDurationById(newRouteId);
                if (routeDuration != null) {
                    schedule.setArrivalTime(schedule.getDepartureTime().plusMinutes(routeDuration));
                }
            }
            if (newDepartureTime != null) {
                schedule.setDepartureTime(schedule.getDepartureTime().plus(timeDifference));
                schedule.setArrivalTime(schedule.getArrivalTime().plus(timeDifference));
            }
        }

        // Guardar cambios en las entidades
        vehicleScheduleRepository.saveAll(schedules);

        response.success(schedules.size() + " horarios actualizados exitosamente.");
        return response;
    }
}
