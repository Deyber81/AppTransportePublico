package com.AppTransporte.AppTransportePublico.Controller;
import com.AppTransporte.AppTransportePublico.Dto.ResponseGeneral;
import com.AppTransporte.AppTransportePublico.Dto.Requets.RequestVehicle;
import com.AppTransporte.AppTransportePublico.Dto.Response.DtoVehicle;
import com.AppTransporte.AppTransportePublico.Service.VehicleService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/Vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping(path = "/InsertVehicle", consumes = "multipart/form-data")
    public ResponseEntity<ResponseGeneral> insertVehicle(
            @Valid @ModelAttribute RequestVehicle requestVehicle,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ResponseGeneral response = new ResponseGeneral();
            bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            response.setMessage("Error al insertar el vehículo.");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            ResponseGeneral response = vehicleService.insertVehicle(requestVehicle);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            ResponseGeneral response = new ResponseGeneral();
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Error interno al intentar insertar el vehículo.");
            response.getErrors().add(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping("/ListAllVehicles")
    public ResponseEntity<ResponseGeneral> listAllVehicles() {
        try {
            List<DtoVehicle> vehicles = vehicleService.listAllVehicles();
    
            ResponseGeneral response = new ResponseGeneral();
    
            // Si la lista está vacía, devolver un mensaje indicando esto
            if (vehicles.isEmpty()) {
                response.setMessage("La lista de vehículos está vacía.");
                response.setData(null);
                response.setStatusCode(HttpStatus.OK);
                return ResponseEntity.ok(response);
            }
    
            // Si hay datos, devolverlos con un mensaje de éxito
            response.setMessage("Lista de vehículos obtenida exitosamente.");
            response.setData(vehicles);
            response.setStatusCode(HttpStatus.OK);
            return ResponseEntity.ok(response);
    
        } catch (Exception e) {
            e.printStackTrace();
    
            // Manejo de error interno
            ResponseGeneral response = new ResponseGeneral();
            response.setMessage("Error al listar los vehículos. Por favor, intente nuevamente.");
            response.setErrors(List.of(e.getMessage()));
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }    
    @DeleteMapping("/DeleteVehicle/{id}")
    public ResponseEntity<ResponseGeneral> deleteVehicle(@PathVariable String id) {
        try {
            ResponseGeneral response = vehicleService.deleteVehicleById(id);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            ResponseGeneral response = new ResponseGeneral();
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Error interno al intentar eliminar el vehículo.");
            response.getErrors().add(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/GetVehicle/{id}")
    public ResponseEntity<ResponseGeneral> getVehicle(@PathVariable String id) {
        try {
            ResponseGeneral response = vehicleService.getVehicleById(id);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            ResponseGeneral response = new ResponseGeneral();
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Error interno al intentar buscar el vehículo.");
            response.getErrors().add(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
