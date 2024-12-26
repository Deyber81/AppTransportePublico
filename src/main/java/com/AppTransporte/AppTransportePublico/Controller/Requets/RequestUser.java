package com.AppTransporte.AppTransportePublico.Controller.Requets;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class RequestUser {

    @NotBlank(message = "El campo 'NameUser' es requerido.")
    @Size(max = 100, message = "El campo 'NameUser' no debe exceder los 100 caracteres.")
    private String nameUser;

    @NotBlank(message = "El campo 'SurnameUser' es requerido.")
    @Size(max = 150, message = "El campo 'SurnameUser' no debe exceder los 150 caracteres.")
    private String surnameUser;

    @NotBlank(message = "El campo 'GmailUser' es requerido.")
    @Email(message = "El campo 'GmailUser' debe ser una dirección de correo electrónico válida.")
    private String gmailUser;

    @NotBlank(message = "El campo 'PasswordUser' es requerido.")
    @Size(min = 8, message = "El campo 'PasswordUser' debe tener al menos 8 caracteres.")
    private String passwordUser;

    @NotBlank(message = "El campo 'TypeUser' es requerido.")
    @Size(max = 50, message = "El campo 'TypeUser' no debe exceder los 50 caracteres.")
    private String typeUser;

    private Date dateCreate;

    private String stateUser;

    @NotBlank(message = "El campo 'PhoneUser' es requerido.")
    @Size(max = 20, message = "El campo 'PhoneUser' no debe exceder los 20 caracteres.")
    private String phoneUser;

    private Date lastLogin;

    private List<RequestVehicle> vehicles;  // Relación con vehículos
}