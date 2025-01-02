package com.AppTransporte.AppTransportePublico.Bussines;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AppTransporte.AppTransportePublico.Dto.Response.DtoUser;
import com.AppTransporte.AppTransportePublico.Entity.TUser;
import com.AppTransporte.AppTransportePublico.Repository.RepoUser;

@Component
public class UserBusiness {
    @Autowired
    private RepoUser repoUser;

    public void validateUserUniqueness(DtoUser dtoUser, List<String> errorMessages) {
        Optional<TUser> existingUserByGmail = repoUser.findByGmailUser(dtoUser.getGmailUser());
        if (existingUserByGmail.isPresent() && !existingUserByGmail.get().getIdUser().equals(dtoUser.getIdUser())) {
            errorMessages.add("El correo electrónico ya está registrado.");
        }

        Optional<TUser> existingUserByPhone = repoUser.findByPhoneUser(dtoUser.getPhoneUser());
        if (existingUserByPhone.isPresent() && !existingUserByPhone.get().getIdUser().equals(dtoUser.getIdUser())) {
            errorMessages.add("El número de teléfono ya está registrado.");
        }
    }
}