package com.AppTransporte.AppTransportePublico.Controller;


import com.AppTransporte.AppTransportePublico.Bussines.General.GenericConverter;
import com.AppTransporte.AppTransportePublico.Bussines.User.RequetsUser.RequestInsertUsers;
import com.AppTransporte.AppTransportePublico.Bussines.User.RequetsUser.RequestUpdateUser;
import com.AppTransporte.AppTransportePublico.Bussines.User.UserResponse.ResponseDeleteUser;
import com.AppTransporte.AppTransportePublico.Bussines.User.UserResponse.ResponseGetAllUsers;
import com.AppTransporte.AppTransportePublico.Bussines.User.UserResponse.ResponseInsertUser;
import com.AppTransporte.AppTransportePublico.Dto.DtoUser;
import com.AppTransporte.AppTransportePublico.Service.UserService;
import com.AppTransporte.AppTransportePublico.Service.GenericService.DuplicateFieldException;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private GenericConverter genericConverter;
    @PostMapping(path = "/insert", consumes = { "multipart/form-data" })
    public ResponseEntity<ResponseInsertUser> insertUser(@Valid @ModelAttribute RequestInsertUsers requestInsertUsers, BindingResult bindingResult) {
        ResponseInsertUser responseInsertUser = new ResponseInsertUser();
    
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> responseInsertUser.addResponseMessage(error.getDefaultMessage()));
            return new ResponseEntity<>(responseInsertUser, HttpStatus.BAD_REQUEST);
        }
    
        try {
            DtoUser dtoUser = genericConverter.convertToDto(requestInsertUsers, DtoUser.class, "nameUser", "surnameUser", "gmailUser", "passwordUser", "typeUser", "phoneUser");
    
            if (userService.insert(dtoUser)) {
                responseInsertUser.success("Usuario insertado exitosamente.");
                responseInsertUser.setInsertData(dtoUser.getIdUser(), "Usuario insertado exitosamente.");
                return new ResponseEntity<>(responseInsertUser, HttpStatus.CREATED);
            } else {
                responseInsertUser.error("Error al insertar el usuario.");
                return new ResponseEntity<>(responseInsertUser, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DuplicateFieldException e) {
            responseInsertUser.error("Error de unicidad: " + e.getMessage());
            return new ResponseEntity<>(responseInsertUser, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            responseInsertUser.error("Ocurrió un error inesperado: " + e.getMessage());
            responseInsertUser.addResponseMessage("Estamos trabajando para solucionarlo.");
            return new ResponseEntity<>(responseInsertUser, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/getall")
    public ResponseEntity<ResponseGetAllUsers> getAllUsers() {
        ResponseGetAllUsers responseGetAllUsers = new ResponseGetAllUsers();
    
        List<DtoUser> listDtoUser = userService.getAll();
    
        if (listDtoUser.isEmpty()) {
             responseGetAllUsers.getDto().setListUsers(new ArrayList<>());
            responseGetAllUsers.warning("No se encontraron usuarios.");
        } else {
            // Se encontraron usuarios
            responseGetAllUsers.getDto().setListUsers(listDtoUser);
            responseGetAllUsers.success("Usuarios obtenidos exitosamente.");
        }
    
        return new ResponseEntity<>(responseGetAllUsers, HttpStatus.OK);
    }
    
    @GetMapping("/search")
    public ResponseEntity<?> searchUsers(
            @RequestParam(required = false) String nameUser,
            @RequestParam(required = false) String surnameUser,
            @RequestParam(required = false) String gmailUser,
            @RequestParam(required = false) String phoneUser) {
        try {
            List<DtoUser> results = userService.searchUsers(nameUser, surnameUser, gmailUser, phoneUser);
            if (results.isEmpty()) {
                return ResponseEntity.ok("No se encontraron usuarios.");
            }
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado.");
        }
    }
    @DeleteMapping(path = "/delete")
    public ResponseEntity<ResponseDeleteUser> deleteUser(@RequestParam String idUser) {
        ResponseDeleteUser responseDeleteUser = new ResponseDeleteUser();

        // Llamamos al servicio para eliminar el usuario
        boolean isDeleted = userService.delete(idUser);

        if (isDeleted) {
            responseDeleteUser.success("Usuario eliminado exitosamente.");
            responseDeleteUser.setDeleteData(idUser, "Usuario eliminado exitosamente.");
            return new ResponseEntity<>(responseDeleteUser, HttpStatus.OK);
        } else {
            responseDeleteUser.error("No se encontró el usuario con el ID proporcionado.");
            responseDeleteUser.setDeleteData(idUser, "Error: Usuario no encontrado.");
            return new ResponseEntity<>(responseDeleteUser, HttpStatus.NOT_FOUND);
        }
    }
  @PutMapping(path = "/update/{id}", consumes = { "multipart/form-data" })
  public ResponseEntity<ResponseInsertUser> updateUser(@PathVariable("id") String id, 
      @Valid @ModelAttribute RequestUpdateUser requestUpdateUser, BindingResult bindingResult) {
  
      ResponseInsertUser responseInsertUser = new ResponseInsertUser();
      
      if (bindingResult.hasErrors()) {
          bindingResult.getAllErrors().forEach(error -> responseInsertUser.addResponseMessage(error.getDefaultMessage()));
          return new ResponseEntity<>(responseInsertUser, HttpStatus.BAD_REQUEST);
      }
  
      try {
          DtoUser dtoUser = genericConverter.convertToDto(requestUpdateUser, DtoUser.class,
              "nameUser", "surnameUser", "gmailUser", "passwordUser", "typeUser", "phoneUser");
  
          dtoUser.setIdUser(id);
  
          if (userService.update(dtoUser)) {
              responseInsertUser.success("Usuario actualizado exitosamente.");
              responseInsertUser.setInsertData(dtoUser.getIdUser(), "Usuario actualizado exitosamente.");
              return new ResponseEntity<>(responseInsertUser, HttpStatus.OK);
          } else {
              responseInsertUser.error("Error al actualizar el usuario.");
              return new ResponseEntity<>(responseInsertUser, HttpStatus.INTERNAL_SERVER_ERROR);
          }
      } catch (DuplicateFieldException e) {
          responseInsertUser.error("Error de unicidad: " + e.getMessage());
          return new ResponseEntity<>(responseInsertUser, HttpStatus.BAD_REQUEST);
      } catch (Exception e) {
          responseInsertUser.error("Ocurrió un error inesperado: " + e.getMessage());
          responseInsertUser.addResponseMessage("Estamos trabajando para solucionarlo.");
          return new ResponseEntity<>(responseInsertUser, HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }
  

}
