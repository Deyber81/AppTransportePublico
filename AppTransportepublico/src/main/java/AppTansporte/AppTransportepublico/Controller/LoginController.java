package AppTansporte.AppTransportepublico.Controller;

import AppTansporte.AppTransportepublico.Dto.Request.RequestUsers.LoginRequest;
import AppTansporte.AppTransportepublico.Dto.Response.DtoLogin;
import AppTansporte.AppTransportepublico.Dto.ResponseGeneral;
import AppTansporte.AppTransportepublico.Services.LoginService;
import AppTansporte.AppTransportepublico.Business.GenericMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private GenericMapper genericMapper;

    @PostMapping(path = "/login", consumes = { "multipart/form-data" })
    public ResponseEntity<ResponseGeneral> login(
            @Valid @ModelAttribute LoginRequest loginRequest,
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
            // Mapear LoginRequest a DtoLogin
            DtoLogin dtoLogin = genericMapper.map(loginRequest, DtoLogin.class);

            // Llamar al servicio de login
            ResponseGeneral serviceResponse = loginService.login(dtoLogin);

            // Verificar si hay errores en el servicio
            if (serviceResponse.getErrors() != null && !serviceResponse.getErrors().isEmpty()) {
                return new ResponseEntity<>(serviceResponse, HttpStatus.BAD_REQUEST);
            }

            // Retornar éxito
            return new ResponseEntity<>(serviceResponse, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            response.error("Error interno del servidor. Por favor, intente nuevamente.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
