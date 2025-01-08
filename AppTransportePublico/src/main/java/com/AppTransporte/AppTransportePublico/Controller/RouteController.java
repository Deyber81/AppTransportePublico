package com.AppTransporte.AppTransportePublico.Controller;

import com.AppTransporte.AppTransportePublico.Dto.ResponseGeneral;
import com.AppTransporte.AppTransportePublico.Dto.Requets.RequestRoute;
import com.AppTransporte.AppTransportePublico.Service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/Route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @PostMapping(path = "/InsertRoute", consumes = "multipart/form-data")
    public ResponseEntity<ResponseGeneral> insertRoute(
            @Valid @ModelAttribute RequestRoute requestRoute,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ResponseGeneral response = new ResponseGeneral();
            bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            response.setMessage("Error en los datos proporcionados.");
            return ResponseEntity.badRequest().body(response);
        }

        ResponseGeneral response = routeService.insertRoute(requestRoute);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/ListAllRoutes")
    public ResponseEntity<ResponseGeneral> listAllRoutes() {
        ResponseGeneral response = routeService.listAllRoutes();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/GetRoute/{id}")
    public ResponseEntity<ResponseGeneral> getRouteById(@PathVariable String id) {
        ResponseGeneral response = routeService.getRouteById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping(path = "/UpdateRoute/{id}", consumes = "multipart/form-data")
    public ResponseEntity<ResponseGeneral> updateRoute(
            @PathVariable String id,
            @Valid @ModelAttribute RequestRoute requestRoute,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ResponseGeneral response = new ResponseGeneral();
            bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            response.setMessage("Error en los datos proporcionados.");
            return ResponseEntity.badRequest().body(response);
        }

        ResponseGeneral response = routeService.updateRoute(requestRoute, id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/DeleteRoute/{id}")
    public ResponseEntity<ResponseGeneral> deleteRoute(@PathVariable String id) {
        ResponseGeneral response = routeService.deleteRouteById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
