package com.AppTransporte.AppTransportePublico.Service.Converter;

import com.AppTransporte.AppTransportePublico.Dto.Requets.RequestUser;
import com.AppTransporte.AppTransportePublico.Dto.Response.DtoUser;
import com.AppTransporte.AppTransportePublico.Entity.TUser;
import com.AppTransporte.AppTransportePublico.Service.GenericService.GenericConverter;
import org.springframework.stereotype.Component;

@Component
public class UserConverter extends GenericConverter<RequestUser, DtoUser, TUser> {

    public UserConverter() {
        super(
            // RequestUser → DtoUser
            request -> {
                DtoUser dto = new DtoUser();
                dto.setIdUser(request.getIdUser());
                dto.setNameUser(request.getNameUser());
                dto.setSurnameUser(request.getSurnameUser());
                dto.setGmailUser(request.getGmailUser());
                dto.setPasswordUser(request.getPasswordUser());
                dto.setTypeUser(request.getTypeUser());
                dto.setPhoneUser(request.getPhoneUser());
                return dto;
            },
            // DtoUser → TUser
            dto -> {
                TUser entity = new TUser();
                entity.setIdUser(dto.getIdUser());
                entity.setNameUser(dto.getNameUser());
                entity.setSurnameUser(dto.getSurnameUser());
                entity.setGmailUser(dto.getGmailUser());
                entity.setPasswordUser(dto.getPasswordUser());
                entity.setTypeUser(dto.getTypeUser());
                entity.setDateCreate(dto.getDateCreate());
                entity.setStateUser(dto.getStateUser());
                entity.setPhoneUser(dto.getPhoneUser());
                entity.setLastLogin(dto.getLastLogin());
                return entity;
            },
            // TUser → DtoUser
            entity -> {
                DtoUser dto = new DtoUser();
                dto.setIdUser(entity.getIdUser());
                dto.setNameUser(entity.getNameUser());
                dto.setSurnameUser(entity.getSurnameUser());
                dto.setGmailUser(entity.getGmailUser());
               // dto.setPasswordUser(entity.getPasswordUser());
                dto.setTypeUser(entity.getTypeUser());
                dto.setDateCreate(entity.getDateCreate());
                dto.setStateUser(entity.getStateUser());
                dto.setPhoneUser(entity.getPhoneUser());
                dto.setLastLogin(entity.getLastLogin());
                return dto;
            }
        );
    }
}
