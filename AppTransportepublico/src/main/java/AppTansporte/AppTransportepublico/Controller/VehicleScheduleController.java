package AppTansporte.AppTransportepublico.Controller;

import AppTansporte.AppTransportepublico.Dto.ResponseGeneral;
import AppTansporte.AppTransportepublico.Dto.Request.RequestVehuckeSchudele.CreateVehicleScheduleRequest;
import AppTansporte.AppTransportepublico.Dto.Response.DtoVehicleSchedule;
import AppTansporte.AppTransportepublico.Services.VehicleScheduleService;
import AppTansporte.AppTransportepublico.Business.GenericMapper;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/VehicleSchedule")
public class VehicleScheduleController {

    @Autowired
    private VehicleScheduleService vehicleScheduleService;

    @Autowired
    private GenericMapper genericMapper;

    /**
     * Inserta un nuevo horario de vehículo.
     */
    @PostMapping(path = "/InsertSchedule", consumes = { "multipart/form-data" })
    public ResponseEntity<ResponseGeneral> createVehicleSchedule(
            @Valid @ModelAttribute CreateVehicleScheduleRequest requestSchedule,
            BindingResult bindingResult) {

        ResponseGeneral response = new ResponseGeneral();

        // Validar errores del Request Object
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            bindingResult.getAllErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));
            response.error(errorMessages);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {

            // Mapear Request a DTO
            DtoVehicleSchedule dtoSchedule = genericMapper.map(requestSchedule, DtoVehicleSchedule.class);

            // Enviar al servicio
            ResponseGeneral serviceResponse = vehicleScheduleService.insertVehicleSchedule(dtoSchedule);

            // Validar errores del servicio
            if (serviceResponse.getErrors() != null && !serviceResponse.getErrors().isEmpty()) {
                return new ResponseEntity<>(serviceResponse, HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(serviceResponse, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            response.error("Error interno del servidor. Por favor, intente nuevamente.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Lista todos los horarios de vehículos.
     */
    @GetMapping("/GetAllSchedules")
    public ResponseEntity<?> listAllSchedules() {
        try {
            List<DtoVehicleSchedule> schedules = vehicleScheduleService.listAllSchedules();

            ResponseGeneral response = new ResponseGeneral();

            if (schedules.isEmpty()) {
                response.success("La lista de horarios está vacía.");
                response.setData(null);
                response.setStatusCode(HttpStatus.OK);
                return ResponseEntity.ok(response);
            }

            response.success("Lista de horarios obtenida exitosamente.");
            response.setData(schedules);
            response.setStatusCode(HttpStatus.OK);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();

            ResponseGeneral response = new ResponseGeneral();
            response.error("Error al listar los horarios. Por favor, intente nuevamente.");
            response.setErrors(List.of(e.getMessage()));
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Elimina un horario por su ID.
     */
    @DeleteMapping("/DeleteSchedule/{id}")
    public ResponseEntity<ResponseGeneral> deleteVehicleSchedule(@PathVariable String id) {
        ResponseGeneral response = vehicleScheduleService.deleteVehicleSchedule(id);

        if (response.getErrors() != null && !response.getErrors().isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping(path = "/DeleteSchedulesByDay", consumes = { "multipart/form-data" })
public ResponseEntity<ResponseGeneral> deleteSchedulesByVehicleAndDay(
        @RequestParam("idVehicle") String idVehicle,
        @RequestParam("dayOfWeek") String dayOfWeek) {

    // Llamar al servicio para eliminar los horarios
    ResponseGeneral response = vehicleScheduleService.deleteSchedulesByVehicleAndDay(idVehicle, dayOfWeek);

    if (response.getErrors() != null && !response.getErrors().isEmpty()) {
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(response, HttpStatus.OK);
}

   
    /**
     * Obtiene un horario por su ID.
     */
    @GetMapping("/SearchScheduleId/{id}")
    public ResponseEntity<ResponseGeneral> getVehicleScheduleById(@PathVariable("id") String idSchedule) {
        ResponseGeneral response = vehicleScheduleService.getVehicleScheduleById(idSchedule);

        if (response.getErrors() != null && !response.getErrors().isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/edit")
        public ResponseEntity<ResponseGeneral> editSchedules(
                @Validated @ModelAttribute CreateVehicleScheduleRequest request) {

            // Mapear el objeto de entrada a DTO
            DtoVehicleSchedule dto = genericMapper.map(request, DtoVehicleSchedule.class);

            // Llamar al servicio
            ResponseGeneral response = vehicleScheduleService.editSchedules(dto);

            return ResponseEntity.status(response.getStatusCode().value()).body(response);
        }
    
}
