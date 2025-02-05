package AppTansporte.AppTransportepublico.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import AppTansporte.AppTransportepublico.Business.GenericMapper;
import AppTansporte.AppTransportepublico.Business.UserBusiness;
import AppTansporte.AppTransportepublico.Dto.ResponseGeneral;
import AppTansporte.AppTransportepublico.Dto.Response.DtoUser;
import AppTansporte.AppTransportepublico.Entity.TUser;
import AppTansporte.AppTransportepublico.Repository.RepoUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private RepoUser repoUser;

    @Autowired
    private GenericMapper genericMapper;

    @Autowired
    private UserBusiness userBusiness;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseGeneral insertUser(DtoUser dtoUser) {
        ResponseGeneral response = new ResponseGeneral();
        List<String> errorMessages = new ArrayList<>();
    
        try {
            // Validar unicidad de usuario
            userBusiness.validateUserUniqueness(dtoUser, errorMessages);
            if (!errorMessages.isEmpty()) {
                response.error(errorMessages);
                return response;
            }
            // Encriptar contraseña
            String encryptedPassword = passwordEncoder.encode(dtoUser.getPasswordUser());
            dtoUser.setPasswordUser(encryptedPassword);
    
            // Completar datos adicionales
            dtoUser.setIdUser(UUID.randomUUID().toString());
            dtoUser.setDateCreate(new Date());
            dtoUser.setLastLogin(new Date());
            dtoUser.setStateUser("Inactivo");
    
            TUser user = genericMapper.map(dtoUser, TUser.class);
    
            repoUser.save(user);
            response.success("Usuario guardado exitosamente.");
            return response;
    
        } catch (Exception e) {
            e.printStackTrace();
            response.error("Error interno del servidor: " + e.getMessage());
            return response;
        }
    }
    

    public List<DtoUser> listAllUsers() {
        List<TUser> users = repoUser.findAll();
        return users.stream()
        .map(user -> {
            DtoUser dtoUser = genericMapper.map(user, DtoUser.class);
            dtoUser.setPasswordUser(null); // Ocultar la contraseña
            return dtoUser;
        })
        .collect(Collectors.toList());
    }

    public ResponseGeneral deleteUserById(String idUser) {
        ResponseGeneral response = new ResponseGeneral();

        Optional<TUser> userOptional = repoUser.findById(idUser);
        if (userOptional.isEmpty()) {
            response.error("Usuario no encontrado.");
            return response;
        }

        repoUser.delete(userOptional.get());

        response.success("Usuario eliminado exitosamente.");
        return response;
    }

    public ResponseGeneral updateUser(DtoUser dtoUser) {
    ResponseGeneral response = new ResponseGeneral();
    List<String> errorMessages = new ArrayList<>();
        try {
            // Verificar si el usuario existe en la base de datos
            Optional<TUser> optionalUser = repoUser.findById(dtoUser.getIdUser());
            if (optionalUser.isEmpty()) {
                response.error("El usuario con el ID especificado no existe.");
                return response;
            }

            // Obtener la entidad actual desde la base de datos
            TUser existingUser = optionalUser.get();

            // Actualizar solo los campos no nulos enviados en el DTO
            if (dtoUser.getNameUser() != null) {
                existingUser.setNameUser(dtoUser.getNameUser());
            }
            if (dtoUser.getSurnameUser() != null) {
                existingUser.setSurnameUser(dtoUser.getSurnameUser());
            }
            if (dtoUser.getGmailUser() != null) {
                existingUser.setGmailUser(dtoUser.getGmailUser());
            }
            if (dtoUser.getPhoneUser() != null) {
                existingUser.setPhoneUser(dtoUser.getPhoneUser());
            }
            if (dtoUser.getTypeUser() != null) {
                existingUser.setTypeUser(dtoUser.getTypeUser());
            }
            if (dtoUser.getPasswordUser() != null && !dtoUser.getPasswordUser().isEmpty()) {
                // Encriptar la nueva contraseña antes de guardar
                String encryptedPassword = passwordEncoder.encode(dtoUser.getPasswordUser());
                existingUser.setPasswordUser(encryptedPassword);
            }

            userBusiness.validateUserUniqueness(dtoUser, errorMessages);
            if (!errorMessages.isEmpty()) {
                response.error(errorMessages);
                return response;
            }
            // Guardar los cambios en la base de datos
            repoUser.save(existingUser);

            // Respuesta exitosa
            response.success("Usuario actualizado exitosamente.");
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            response.error("Error interno del servidor: " + e.getMessage());
            return response;
        }
    }
    public ResponseGeneral getUserById(String idUser) {
        ResponseGeneral response = new ResponseGeneral();

        Optional<TUser> userOptional = repoUser.findById(idUser);
        if (userOptional.isEmpty()) {
            response.error("Usuario no encontrado.");
            return response;
        }

        // Convertir entidad a DTO
        DtoUser dtoUser = genericMapper.map(userOptional.get(), DtoUser.class);

        response.success("Usuario encontrado.");
        response.setData(dtoUser);
        return response;
    }
}

