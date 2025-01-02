package com.AppTransporte.AppTransportePublico.Dto.Response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoUser {

    private String idUser;

    private String nameUser;

    private String surnameUser;

    private String gmailUser;

    private String passwordUser;

    private String typeUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
    private Date dateCreate;

    private String stateUser;

    private String phoneUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLogin;

}
