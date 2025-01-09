package AppTansporte.AppTransportepublico.Business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import AppTansporte.AppTransportepublico.Dto.Response.DtoUser;
import AppTansporte.AppTransportepublico.Entity.TUser;
import AppTansporte.AppTransportepublico.Repository.RepoUser;

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