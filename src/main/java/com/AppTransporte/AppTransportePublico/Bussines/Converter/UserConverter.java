package com.AppTransporte.AppTransportePublico.Bussines.Converter;

import com.AppTransporte.AppTransportePublico.Entity.TUser;
import com.AppTransporte.AppTransportePublico.Bussines.User.RequetsUser.RequestInsertUsers;
import com.AppTransporte.AppTransportePublico.Dto.DtoUser;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    // Convertir de DtoUser a TUser
    public TUser convertToEntity(DtoUser dtoUser) {
        TUser tUser = new TUser();
        tUser.setIdUser(dtoUser.getIdUser());
        tUser.setNameUser(dtoUser.getNameUser());
        tUser.setSurnameUser(dtoUser.getSurnameUser());
        tUser.setGmailUser(dtoUser.getGmailUser());
        tUser.setPasswordUser(dtoUser.getPasswordUser());
        tUser.setTypeUser(dtoUser.getTypeUser());
        tUser.setDateCreate(dtoUser.getDateCreate());
        tUser.setStateUser(dtoUser.getStateUser());
        tUser.setPhoneUser(dtoUser.getPhoneUser());
        tUser.setLastLogin(dtoUser.getLastLogin());
        return tUser;
    }

    // Convertir de TUser a DtoUser
    public DtoUser convertToDto(TUser tUser) {
        DtoUser dtoUser = new DtoUser();
        dtoUser.setIdUser(tUser.getIdUser());
        dtoUser.setNameUser(tUser.getNameUser());
        dtoUser.setSurnameUser(tUser.getSurnameUser());
        dtoUser.setGmailUser(tUser.getGmailUser());
        dtoUser.setPasswordUser(tUser.getPasswordUser());
        dtoUser.setTypeUser(tUser.getTypeUser());
        dtoUser.setDateCreate(tUser.getDateCreate());
        dtoUser.setStateUser(tUser.getStateUser());
        dtoUser.setPhoneUser(tUser.getPhoneUser());
        dtoUser.setLastLogin(tUser.getLastLogin());
        return dtoUser;
    }
     // Convertir de RequestInsertUsers a DtoUser
    public DtoUser convertToDto(RequestInsertUsers requestInsertUsers) {
        DtoUser dtoUser = new DtoUser();
        dtoUser.setNameUser(requestInsertUsers.getNameUser());
        dtoUser.setSurnameUser(requestInsertUsers.getSurnameUser());
        dtoUser.setGmailUser(requestInsertUsers.getGmailUser());
        dtoUser.setPasswordUser(requestInsertUsers.getPasswordUser());
        dtoUser.setTypeUser(requestInsertUsers.getTypeUser());
        dtoUser.setPhoneUser(requestInsertUsers.getPhoneUser());
        return dtoUser;
    }
}
