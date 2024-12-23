package com.AppTransporte.AppTransportePublico.Service;

import com.AppTransporte.AppTransportePublico.Repository.RepoUser;
import com.AppTransporte.AppTransportePublico.Service.GenericService.DuplicateFieldException;
import com.AppTransporte.AppTransportePublico.Bussines.Converter.UserConverter;
import com.AppTransporte.AppTransportePublico.Dto.DtoUser;
import com.AppTransporte.AppTransportePublico.Entity.TUser;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Date;

@Service
public class UserService {

    @Autowired
    private RepoUser repoUser;
    @Autowired
    private UserConverter userConverter;

    public boolean insert(DtoUser dtoUser) {
        
        boolean correoEnUso = repoUser.existsByGmailUser(dtoUser.getGmailUser());
        boolean telefonoEnUso = repoUser.existsByPhoneUser(dtoUser.getPhoneUser());

        if (correoEnUso && telefonoEnUso) {
            throw new DuplicateFieldException("El correo electrónico y el número de teléfono ya están registrados.");
        }
        if (correoEnUso) {
            throw new DuplicateFieldException("El correo electrónico ya está registrado.");
        }
        if (telefonoEnUso) {
            throw new DuplicateFieldException("El número de teléfono ya está registrado.");
        }
        dtoUser.setIdUser(UUID.randomUUID().toString());
        dtoUser.setDateCreate(new Date());
        dtoUser.setLastLogin(new Date());
        dtoUser.setStateUser("Inactivo");
        TUser tUser = userConverter.convertToEntity(dtoUser);
        repoUser.save(tUser);
        return true;
    }

    // Método para obtener todos los usuarios
    public List<DtoUser> getAll() {
        List<TUser> listTUser = repoUser.findAll();

        // Convertimos la lista de TUser a DtoUser utilizando Streams
        return listTUser.stream()
                .map(userConverter::convertToDto)
                .collect(Collectors.toList());
    }
    // Método para buscar un usuario
   public List<DtoUser> searchUsers(String nameUser, String surnameUser, String gmailUser, String phoneUser) {
        List<TUser> results = new ArrayList<>();

        if (nameUser != null && !nameUser.isEmpty()) {
            results.addAll(repoUser.findByNameUserContainingIgnoreCase(nameUser));
        }
        if (surnameUser != null && !surnameUser.isEmpty()) {
            results.addAll(repoUser.findBySurnameUserContainingIgnoreCase(surnameUser));
        }
        if (gmailUser != null && !gmailUser.isEmpty()) {
            results.addAll(repoUser.findByGmailUserContainingIgnoreCase(gmailUser));
        }
        if (phoneUser != null && !phoneUser.isEmpty()) {
            results.addAll(repoUser.findByPhoneUserContaining(phoneUser));
        }

        // Eliminar duplicados (si buscas en varios campos, un usuario puede aparecer varias veces)
        results = results.stream().distinct().collect(Collectors.toList());

        return results.stream().map(userConverter::convertToDto).collect(Collectors.toList());
    }
    // Método para eliminar un usuario
    public boolean delete(String idUser) {
        Optional<TUser> userOptional = repoUser.findById(idUser);

        if (userOptional.isPresent()) {
            repoUser.deleteById(idUser);
            return true;
        } else {
            return false;
        }
    }
    // Método para editar un usuario
    public boolean update(DtoUser dtoUser) {
        Optional<TUser> optionalTUser = repoUser.findById(dtoUser.getIdUser());

        if (optionalTUser.isEmpty()) {
            throw new EntityNotFoundException("Usuario no encontrado con el ID: " + dtoUser.getIdUser());
        }
        Optional<TUser> userWithSameGmail = repoUser.findByGmailUser(dtoUser.getGmailUser());
        if (userWithSameGmail.isPresent() && !userWithSameGmail.get().getIdUser().equals(dtoUser.getIdUser())) {
            throw new DuplicateFieldException("El correo electrónico ya está registrado.");
        }
        if (repoUser.findByPhoneUser(dtoUser.getPhoneUser()).isPresent() && 
        !repoUser.findByPhoneUser(dtoUser.getPhoneUser()).get().getIdUser().equals(dtoUser.getIdUser())) {
        throw new DuplicateFieldException("El número de teléfono ya está registrado.");
        }
        TUser existingUser = optionalTUser.get();
        existingUser.setNameUser(dtoUser.getNameUser());
        existingUser.setSurnameUser(dtoUser.getSurnameUser());
        existingUser.setGmailUser(dtoUser.getGmailUser());
        existingUser.setPasswordUser(dtoUser.getPasswordUser());
        existingUser.setTypeUser(dtoUser.getTypeUser());
        existingUser.setPhoneUser(dtoUser.getPhoneUser());
        existingUser.setLastLogin(new Date());
        repoUser.save(existingUser);
        return true;
    }
}
