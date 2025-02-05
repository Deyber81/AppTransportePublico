package AppTansporte.AppTransportepublico.Services;

import AppTansporte.AppTransportepublico.Business.GenericMapper;
import AppTansporte.AppTransportepublico.Dto.ResponseGeneral;
import AppTansporte.AppTransportepublico.Dto.Response.DtoLogin;
import AppTansporte.AppTransportepublico.Dto.Response.DtoUser;
import AppTansporte.AppTransportepublico.Entity.TUser;
import AppTansporte.AppTransportepublico.Repository.RepoUser;
import AppTansporte.AppTransportepublico.Security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
@Service
public class LoginService {

    @Autowired
    private RepoUser repoUser;

    @Autowired
    private GenericMapper genericMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public ResponseGeneral login(DtoLogin dtoLogin) {
        ResponseGeneral response = new ResponseGeneral();

        try {
            // Buscar usuario por correo
            Optional<TUser> userOptional = repoUser.findByGmailUser(dtoLogin.getGmailUser());

            if (userOptional.isEmpty()) {
                response.error("Credenciales incorrectas. Verifique su correo.");
                return response;
            }

            TUser user = userOptional.get();

            // Validar contraseña encriptada
            if (!passwordEncoder.matches(dtoLogin.getPasswordUser(), user.getPasswordUser())) {
                response.error("Credenciales incorrectas. Verifique su contraseña.");
                return response;
            }

            // Actualizar último inicio de sesión y cambiar estado a Activo
            user.setLastLogin(new Date());
            user.setStateUser("Activo");
            repoUser.save(user);

            // Generar el token JWT con datos adicionales
            String token = jwtTokenProvider.generateToken(
                user.getGmailUser(),
                user.getTypeUser(),
                user.getNameUser(),
                user.getSurnameUser()
            );

            // Mapear usuario a DTO de respuesta
            DtoUser dtoUser = genericMapper.map(user, DtoUser.class);

            // Construir datos relevantes para la respuesta
            DtoUser responseUser = new DtoUser();
            responseUser.setNameUser(dtoUser.getNameUser());
            responseUser.setSurnameUser(dtoUser.getSurnameUser());
            responseUser.setGmailUser(dtoUser.getGmailUser());
            responseUser.setStateUser(dtoUser.getStateUser());
            responseUser.setLastLogin(dtoUser.getLastLogin());
            responseUser.setTypeUser(dtoUser.getTypeUser());

            // Construir respuesta
            response.success("Inicio de sesión exitoso.");
            response.setData(responseUser); // Incluir datos relevantes en la respuesta
            response.addExtra("token", token); // Incluir el token en los extras
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            response.error("Error interno del servidor: " + e.getMessage());
            return response;
        }
    }
}
