package com.AppTransporte.AppTransportePublico.Bussines.User.RequetsUser;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUpdateUser {

    @Size(max = 100, message = "El campo 'NameUser' no debe exceder los 100 caracteres.")
    private String nameUser;

    @Size(max = 150, message = "El campo 'SurnameUser' no debe exceder los 150 caracteres.")
    private String surnameUser;

    @Email(message = "El campo 'GmailUser' debe ser una dirección de correo electrónico válida.")
    private String gmailUser;

    @Size(min = 8, message = "El campo 'PasswordUser' debe tener al menos 8 caracteres.")
    private String passwordUser;

    @Size(max = 50, message = "El campo 'TypeUser' no debe exceder los 50 caracteres.")
    private String typeUser;

    private Date dateCreate;
    
    private String stateUser;

    @Size(max = 20, message = "El campo 'PhoneUser' no debe exceder los 20 caracteres.")
    private String phoneUser;

    private Date lastLogin;

}
