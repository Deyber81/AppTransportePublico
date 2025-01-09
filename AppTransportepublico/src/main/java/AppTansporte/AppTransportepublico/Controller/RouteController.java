package AppTansporte.AppTransportepublico.Controller;

import AppTansporte.AppTransportepublico.Dto.ResponseGeneral;
import AppTansporte.AppTransportepublico.Dto.Request.RequestRoute.CreateRouteRequest;
import AppTansporte.AppTransportepublico.Dto.Response.DtoRoute;
import AppTansporte.AppTransportepublico.Services.RouteService;
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
@RequestMapping("/Route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @Autowired
    private GenericMapper genericMapper;

    /**
     * Inserta una nueva ruta.
     */
    @PostMapping(path = "/InsertRoute", consumes = { "multipart/form-data" })
    public ResponseEntity<ResponseGeneral> insertRoute(
            @Valid @ModelAttribute CreateRouteRequest requestRoute,
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
            // Mapear Request a DTO
            DtoRoute dtoRoute = genericMapper.map(requestRoute, DtoRoute.class);

            // Enviar al servicio
            ResponseGeneral serviceResponse = routeService.insertRoute(dtoRoute);

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
     * Lista todas las rutas.
     */
    @GetMapping("/GetAllRoutes")
    public ResponseEntity<?> listAllRoutes() {
        try {
            List<DtoRoute> routes = routeService.listAllRoutes();

            ResponseGeneral response = new ResponseGeneral();

            if (routes.isEmpty()) {
                response.setMessage("La lista de rutas está vacía.");
                response.setData(null);
                response.setStatusCode(HttpStatus.OK);
                return ResponseEntity.ok(response);
            }

            response.setMessage("Lista de rutas obtenida exitosamente.");
            response.setData(routes);
            response.setStatusCode(HttpStatus.OK);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();

            ResponseGeneral response = new ResponseGeneral();
            response.setMessage("Error al listar las rutas. Por favor, intente nuevamente.");
            response.setErrors(List.of(e.getMessage()));
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Elimina una ruta por su ID.
     */
    @DeleteMapping("/DeleteRoute/{id}")
    public ResponseEntity<ResponseGeneral> deleteRoute(@PathVariable String id) {
        ResponseGeneral response = routeService.deleteRouteById(id);

        if (response.getErrors() != null && !response.getErrors().isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Actualiza una ruta existente.
     */
    @PostMapping(path = "/UpdateRoute/{id}", consumes = { "multipart/form-data" })
    public ResponseEntity<ResponseGeneral> updateRoute(
            @PathVariable("id") String idRoute,
            @Valid @ModelAttribute CreateRouteRequest requestRoute,
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
            DtoRoute dtoRoute = genericMapper.map(requestRoute, DtoRoute.class);
            dtoRoute.setIdRoute(idRoute);

            // Llamar al servicio para actualizar
            ResponseGeneral serviceResponse = routeService.updateRoute(dtoRoute);

            if (serviceResponse.getErrors() != null && !serviceResponse.getErrors().isEmpty()) {
                return new ResponseEntity<>(serviceResponse, HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(serviceResponse, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            response.error("Error interno del servidor. Por favor, intente nuevamente.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene una ruta por su ID.
     */
    @GetMapping("/SearchRouteId/{id}")
    public ResponseEntity<ResponseGeneral> getRouteById(@PathVariable("id") String idRoute) {
        ResponseGeneral response = routeService.getRouteById(idRoute);

        if (response.getErrors() != null && !response.getErrors().isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
