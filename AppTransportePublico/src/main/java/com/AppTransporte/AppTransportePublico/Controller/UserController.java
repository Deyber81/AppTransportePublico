package com.AppTransporte.AppTransportePublico.Controller;

import com.AppTransporte.AppTransportePublico.Dto.ResponseGeneral;
import com.AppTransporte.AppTransportePublico.Dto.Requets.RequestUser;
import com.AppTransporte.AppTransportePublico.Dto.Response.DtoUser;
import com.AppTransporte.AppTransportePublico.Entity.TUser;
import com.AppTransporte.AppTransportePublico.Service.UserService;
import com.AppTransporte.AppTransportePublico.Service.GenericService.GenericConverter;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/User")
public class UserController {

  @Autowired
  private UserService userService;
  @Autowired
  private GenericConverter<RequestUser, DtoUser, TUser> genericConverter;
    @PostMapping(path = "/InsertUser", consumes = { "multipart/form-data" })
    public ResponseEntity<ResponseGeneral> insertUser(@Valid @ModelAttribute RequestUser requestUser, BindingResult bindingResult) {
        ResponseGeneral response = new ResponseGeneral();
        
        if (bindingResult.hasErrors()) {
        List<String> errorMessages = new ArrayList<>();

        bindingResult.getAllErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));

        response.error(errorMessages);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        
        try {
            DtoUser dtoUser = genericConverter.toDTO(requestUser);

            ResponseGeneral result = userService.InsertUser(dtoUser);

            return new ResponseEntity<>(result, result.getStatusCode());
        
        } catch (Exception e) {
            e.printStackTrace();
            response.error("Error interno del servidor. Por favor, intente nuevamente.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/GetAllUsers")
    public ResponseEntity<ResponseGeneral> listAllUsers() {
        try {
            List<DtoUser> users = userService.listAllUsers();

            ResponseGeneral response = new ResponseGeneral();

            // Si la lista está vacía, devolver un mensaje indicando esto
            if (users.isEmpty()) {
                response.setMessage("La lista de usuarios está vacía.");
                response.setData(null);
                response.setStatusCode(HttpStatus.OK);
                return ResponseEntity.ok(response);
            }

            // Si hay datos, devolverlos con un mensaje de éxito
            response.setMessage("Lista de usuarios obtenida exitosamente.");
            response.setData(users);
            response.setStatusCode(HttpStatus.OK);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();

            // Manejo de error interno
            ResponseGeneral response = new ResponseGeneral();
            response.setMessage("Error al listar los usuarios. Por favor, intente nuevamente.");
            response.setErrors(List.of(e.getMessage()));
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/DeleteUser/{id}")
    public ResponseEntity<ResponseGeneral> deleteUser(@PathVariable String id) {
    ResponseGeneral response = userService.deleteUserById(id);

        if (response.getStatusCode() == HttpStatus.OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
   
    @PostMapping(path = "/EditUser/{id}", consumes = { "multipart/form-data" })
    public ResponseEntity<ResponseGeneral> EditUser(@PathVariable("id") String idUser, @Valid @ModelAttribute RequestUser requestUser, BindingResult bindingResult) {
        ResponseGeneral response = new ResponseGeneral();
        if (idUser == null || idUser.isEmpty()) {
            response.error("El ID del usuario no puede ser nulo.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    
        requestUser.setIdUser(idUser);
    
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            bindingResult.getAllErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));
            response.error(errorMessages);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    
        try {
            DtoUser dtoUser = genericConverter.toDTO(requestUser);
    
            ResponseGeneral result = userService.updateUser(dtoUser);
            return new ResponseEntity<>(result, result.getStatusCode());
    
        } catch (Exception e) {
            e.printStackTrace();
            response.error("Error al actualizar el usuario. Por favor, intente nuevamente.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/SearchUserId/{id}")
    public ResponseEntity<ResponseGeneral> getUserById(@PathVariable("id") String idUser) {
        ResponseGeneral response = userService.getUserById(idUser);
        if (response.getErrors() != null && !response.getErrors().isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); 
        }
        return new ResponseEntity<>(response, HttpStatus.OK);  
    }
}

