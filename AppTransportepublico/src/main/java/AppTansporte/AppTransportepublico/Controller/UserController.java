package AppTansporte.AppTransportepublico.Controller;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import AppTansporte.AppTransportepublico.Business.GenericMapper;
import AppTansporte.AppTransportepublico.Dto.ResponseGeneral;
import AppTansporte.AppTransportepublico.Dto.Request.RequestUsers.CreateUserRequest;
import AppTansporte.AppTransportepublico.Dto.Request.RequestUsers.UpdateUserRequest;
import AppTansporte.AppTransportepublico.Dto.Response.DtoUser;
import AppTansporte.AppTransportepublico.Services.UserService;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private GenericMapper genericMapper;

    // Solo administradores pueden insertar usuarios
    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping(path = "/InsertUser", consumes = { "multipart/form-data" })
    public ResponseEntity<ResponseGeneral> insertUser(
            @Valid @ModelAttribute CreateUserRequest requestUser,
            BindingResult bindingResult) {
        
        ResponseGeneral response = new ResponseGeneral();
     if (bindingResult.hasErrors()) {
        List<String> errorMessages = bindingResult.getAllErrors()
            .stream()
            .map(ObjectError::getDefaultMessage)
            .collect(Collectors.toList());
        response.error(errorMessages);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
        try {
            DtoUser dtoUser = genericMapper.map(requestUser, DtoUser.class);
            ResponseGeneral serviceResponse = userService.insertUser(dtoUser);

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

    // Solo administradores pueden listar todos los usuarios
    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("/GetAllUsers")
    public ResponseEntity<?> listAllUsers() {
        try {
            List<DtoUser> users = userService.listAllUsers();

            ResponseGeneral response = new ResponseGeneral();

            if (users.isEmpty()) {
                response.setMessage("La lista de usuarios está vacía.");
                response.setData(null);
                response.setStatusCode(HttpStatus.OK);
                return ResponseEntity.ok(response);
            }

            response.setMessage("Lista de usuarios obtenida exitosamente.");
            response.setData(users);
            response.setStatusCode(HttpStatus.OK);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            ResponseGeneral response = new ResponseGeneral();
            response.setMessage("Error al listar los usuarios. Por favor, intente nuevamente.");
            response.setErrors(List.of(e.getMessage()));
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Solo administradores pueden eliminar usuarios
    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping("/DeleteUser/{id}")
    public ResponseEntity<ResponseGeneral> deleteUser(@PathVariable String id) {
        ResponseGeneral response = userService.deleteUserById(id);

        if (response.getStatusCode() == HttpStatus.OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Solo administradores pueden actualizar usuarios
    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping(path = "/UpdateUser/{id}", consumes = { "multipart/form-data" })
    public ResponseEntity<ResponseGeneral> updateUser(
            @PathVariable("id") String idUser, 
            @Valid @ModelAttribute UpdateUserRequest request, 
            BindingResult bindingResult) {
        
        ResponseGeneral response = new ResponseGeneral();

        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            bindingResult.getAllErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));
            response.error(errorMessages);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            DtoUser dtoUser = genericMapper.map(request, DtoUser.class);
            dtoUser.setIdUser(idUser);
            ResponseGeneral serviceResponse = userService.updateUser(dtoUser);

            return new ResponseEntity<>(serviceResponse, serviceResponse.getStatusCode());

        } catch (Exception e) {
            e.printStackTrace();
            response.error("Error interno del servidor. Por favor, intente nuevamente.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Solo administradores pueden buscar usuarios por ID
    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("/SearchUserId/{id}")
    public ResponseEntity<ResponseGeneral> getUserById(@PathVariable("id") String idUser) {
        ResponseGeneral response = userService.getUserById(idUser);
        if (response.getErrors() != null && !response.getErrors().isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
