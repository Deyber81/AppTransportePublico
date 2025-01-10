package AppTansporte.AppTransportepublico.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import AppTansporte.AppTransportepublico.Business.GenericMapper;
import AppTansporte.AppTransportepublico.Dto.ResponseGeneral;
import AppTansporte.AppTransportepublico.Dto.Request.RequetsStop.CreateStopRequest;
import AppTansporte.AppTransportepublico.Dto.Response.DtoStop;
import AppTansporte.AppTransportepublico.Services.StopService;
import jakarta.validation.Valid;
@RestController
@RequestMapping("/Stop")
public class StopController {
    @Autowired
    private StopService stopService;

    @Autowired
    private GenericMapper genericMapper;
    
    @PostMapping(path = "/InserStop", consumes = { "multipart/form-data" })
    public ResponseEntity<ResponseGeneral> insertStop(
        @Valid @ModelAttribute CreateStopRequest requestStop,
        BindingResult bindingResult) {
    
        ResponseGeneral response = new ResponseGeneral();
        System.out.println("Datos recibidos: " + requestStop);
    
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            bindingResult.getAllErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));
            response.error(errorMessages);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    
        try {
            // Mapear Request a DTO
            DtoStop dtoStop = genericMapper.map(requestStop, DtoStop.class);
    
            // Enviar al servicio
            ResponseGeneral serviceResponse = stopService.insetStop(dtoStop);
    
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
    @GetMapping("/GetAllStops")
    public ResponseEntity<?> listAllStops() {
        try {
            List<DtoStop> stops = stopService.listAllStops();

            ResponseGeneral response = new ResponseGeneral();

            if (stops.isEmpty()) {
                response.setMessage("La lista de paradas está vacía.");
                response.setData(null);
                response.setStatusCode(HttpStatus.OK);
                return ResponseEntity.ok(response);
            }

            response.setMessage("Lista de paradas obtenida exitosamente.");
            response.setData(stops);
            response.setStatusCode(HttpStatus.OK);
            return ResponseEntity.ok(response);

            } catch (Exception e) {
            e.printStackTrace();

            // Manejo de error interno
            ResponseGeneral response = new ResponseGeneral();
            response.setMessage("Error al listar las paradas. Por favor, intente nuevamente.");
            response.setErrors(List.of(e.getMessage()));
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
    }
    @DeleteMapping("/DeleteStop/{id}")
    public ResponseEntity<ResponseGeneral> deleteStop(@PathVariable String id) {
        ResponseGeneral response = stopService.deleteStop(id);

        if (response.getStatusCode() == HttpStatus.OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(path = "/UpdateStop/{id}", consumes = { "multipart/form-data" })
    public ResponseEntity<ResponseGeneral> updateStop(
            @PathVariable("id") String idStop, 
            @Valid @ModelAttribute CreateStopRequest request, 
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
            DtoStop dtoStop = genericMapper.map(request, DtoStop.class);

            // Asignar el ID desde la ruta
            dtoStop.setIdStop(idStop);

            // Llamar al servicio para actualizar
            ResponseGeneral serviceResponse = stopService.updateStop(dtoStop);

            return new ResponseEntity<>(serviceResponse, serviceResponse.getStatusCode());

        } catch (Exception e) {
            e.printStackTrace();
            response.error("Error interno del servidor. Por favor, intente nuevamente.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/SearchStopId/{id}")
    public ResponseEntity<ResponseGeneral> getStopById(@PathVariable("id") String idStop) {
        ResponseGeneral response = stopService.getStopById(idStop);
        if (response.getErrors() != null && !response.getErrors().isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
