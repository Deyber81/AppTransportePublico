package com.AppTransporte.AppTransportePublico.Service;

import com.AppTransporte.AppTransportePublico.Bussines.UserBusiness;
import com.AppTransporte.AppTransportePublico.Dto.ResponseGeneral;
import com.AppTransporte.AppTransportePublico.Dto.Requets.RequestUser;
import com.AppTransporte.AppTransportePublico.Dto.Response.DtoUser;
import com.AppTransporte.AppTransportePublico.Entity.TUser;
import com.AppTransporte.AppTransportePublico.Repository.RepoUser;
import com.AppTransporte.AppTransportePublico.Service.GenericService.GenericConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    private GenericConverter<RequestUser, DtoUser, TUser> genericConverter;
     @Autowired
    private UserBusiness userBusiness;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public ResponseGeneral InsertUser(DtoUser dtoUser) {
        ResponseGeneral response = new ResponseGeneral();
        List<String> errorMessages = new ArrayList<>();
        
        userBusiness.validateUserUniqueness(dtoUser, errorMessages);
        if (!errorMessages.isEmpty()) {
            response.error(errorMessages);
            return response;
        }

        if (!errorMessages.isEmpty()) {
            response.error(errorMessages);
            return response;
        }
        String encryptedPassword = passwordEncoder.encode(dtoUser.getPasswordUser());
        dtoUser.setPasswordUser(encryptedPassword); 
        dtoUser.setIdUser(UUID.randomUUID().toString());
        dtoUser.setDateCreate(new Date());
        dtoUser.setLastLogin(new Date());
        dtoUser.setStateUser("Inactivo");
        dtoUser.setPasswordUser(dtoUser.getPasswordUser());
        TUser user = genericConverter.toEntity(dtoUser);  
        repoUser.save(user);

        response.success("Usuario guardado exitosamente.");
        return response;
    }

    public List<DtoUser> listAllUsers() {
        List<TUser> users = repoUser.findAll();
        return users.stream()
            .map(user -> {
                DtoUser dto = genericConverter.toDTOFromEntity(user);
                return dto;
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
    
        // Verifica si el usuario existe
        Optional<TUser> existingUser = repoUser.findById(dtoUser.getIdUser());
    
        if (!existingUser.isPresent()) {
            response.error("Usuario no encontrado.");
            return response;
        }
        
        // Validación de unicidad de usuario (correo y teléfono)
        userBusiness.validateUserUniqueness(dtoUser, errorMessages);
        if (!errorMessages.isEmpty()) {
            response.error(errorMessages);
            return response;
        }
    
        TUser user = existingUser.get();
        
        // Verificar si la contraseña ha sido modificada antes de encriptarla
        if (dtoUser.getPasswordUser() != null && !dtoUser.getPasswordUser().isEmpty()) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encryptedPassword = passwordEncoder.encode(dtoUser.getPasswordUser());  // Encriptar la nueva contraseña
            user.setPasswordUser(encryptedPassword);
        }
    
        // Actualización de otros datos del usuario
        user.setNameUser(dtoUser.getNameUser());
        user.setSurnameUser(dtoUser.getSurnameUser());
        user.setGmailUser(dtoUser.getGmailUser());
        user.setPhoneUser(dtoUser.getPhoneUser());
        user.setTypeUser(dtoUser.getTypeUser());
        
        // Guardar los cambios en la base de datos
        repoUser.save(user);
        
        // Respuesta exitosa
        response.success("Usuario actualizado exitosamente.");
        return response;
    }
    public ResponseGeneral getUserById(String idUser) {
        ResponseGeneral response = new ResponseGeneral();
        
        // Buscar usuario por ID
        Optional<TUser> userOptional = repoUser.findById(idUser);
        
        if (userOptional.isEmpty()) {
            response.error("Usuario no encontrado.");
            return response;
        }
        
        // Convertir el usuario de la entidad TUser a DtoUser
        DtoUser dtoUser = genericConverter.toDTOFromEntity(userOptional.get());

        response.success("Usuario encontrado.");
        response.setData(dtoUser);  
        return response;
    }
}
