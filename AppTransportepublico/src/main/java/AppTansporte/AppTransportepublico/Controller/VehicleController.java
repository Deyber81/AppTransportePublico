package AppTansporte.AppTransportepublico.Controller;

import AppTansporte.AppTransportepublico.Dto.Response.DtoVehicle;
import AppTansporte.AppTransportepublico.Dto.ResponseGeneral;
import AppTansporte.AppTransportepublico.Dto.Request.RequestVehicle.CreateVehicleRequest;
import AppTansporte.AppTransportepublico.Services.VehicleService;
import AppTansporte.AppTransportepublico.Business.GenericMapper;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private GenericMapper genericMapper;

    @PostMapping(path = "/InsertVehicle", consumes = { "multipart/form-data" })
    public ResponseEntity<ResponseGeneral> insertVehicle(
        @Valid @ModelAttribute CreateVehicleRequest requestVehicle,
        BindingResult bindingResult) {

        ResponseGeneral response = new ResponseGeneral();

        // Validar el Request Object
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            bindingResult.getAllErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));
            response.error(errorMessages);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            // Mapear CreateVehicleRequest a DtoVehicle
            DtoVehicle dtoVehicle = genericMapper.map(requestVehicle, DtoVehicle.class);

            // Enviar al servicio
            ResponseGeneral serviceResponse = vehicleService.insertVehicle(dtoVehicle);

            // Si hay errores, responde con BAD_REQUEST
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

    @GetMapping("/GetAllVehicles")
    public ResponseEntity<?> listAllVehicles() {
        try {
            List<DtoVehicle> vehicles = vehicleService.listAllVehicles();

            ResponseGeneral response = new ResponseGeneral();

            if (vehicles.isEmpty()) {
                response.setMessage("La lista de vehículos está vacía.");
                response.setData(null);
                response.setStatusCode(HttpStatus.OK);
                return ResponseEntity.ok(response);
            }

            response.setMessage("Lista de vehículos obtenida exitosamente.");
            response.setData(vehicles);
            response.setStatusCode(HttpStatus.OK);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();

            ResponseGeneral response = new ResponseGeneral();
            response.setMessage("Error al listar los vehículos. Por favor, intente nuevamente.");
            response.setErrors(List.of(e.getMessage()));
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/DeleteVehicle/{id}")
    public ResponseEntity<ResponseGeneral> deleteVehicle(@PathVariable String id) {
        ResponseGeneral response = vehicleService.deleteVehicleById(id);

        if (response.getStatusCode() == HttpStatus.OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/UpdateVehicle/{id}", consumes = { "multipart/form-data" })
    public ResponseEntity<ResponseGeneral> updateVehicle(
            @PathVariable("id") String idVehicle,
            @Valid @ModelAttribute CreateVehicleRequest requestVehicle,
            BindingResult bindingResult) {

        ResponseGeneral response = new ResponseGeneral();

        // Validar si hay errores en los datos enviados
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            bindingResult.getAllErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));
            response.error(errorMessages);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            // Mapear Request a DTO
            DtoVehicle dtoVehicle = genericMapper.map(requestVehicle, DtoVehicle.class);

            // Asignar el ID desde la ruta
            dtoVehicle.setIdVehicle(idVehicle);

            // Llamar al servicio para actualizar
            ResponseGeneral serviceResponse = vehicleService.updateVehicle(dtoVehicle);

            return new ResponseEntity<>(serviceResponse, serviceResponse.getStatusCode());

        } catch (Exception e) {
            e.printStackTrace();
            response.error("Error interno del servidor. Por favor, intente nuevamente.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/SearchVehicleId/{id}")
    public ResponseEntity<ResponseGeneral> getVehicleById(@PathVariable("id") String idVehicle) {
        ResponseGeneral response = vehicleService.getVehicleById(idVehicle);
        if (response.getErrors() != null && !response.getErrors().isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
