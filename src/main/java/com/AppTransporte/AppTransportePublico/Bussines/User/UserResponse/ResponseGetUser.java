package com.AppTransporte.AppTransportePublico.Bussines.User.UserResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseGetUser extends com.AppTransporte.AppTransportePublico.Bussines.ResponseGeneral {

    // Clase interna para los detalles de la respuesta
    @Getter
    @Setter
    public static class Dto {
        private String userId;  // ID del usuario encontrado
        private String nameUser;  // Nombre del usuario
        private String surnameUser;  // Apellido del usuario
        private String gmailUser;  // Correo electrónico del usuario
        private String message;  // Mensaje de confirmación (exitoso o error)
    }

    private Dto dto;

    public ResponseGetUser() {
        this.dto = new Dto();
    }

    // Método para establecer los datos de búsqueda del usuario
    public void setGetUserData(String userId, String nameUser, String surnameUser, String gmailUser, String message) {
        this.dto.setUserId(userId);
        this.dto.setNameUser(nameUser);
        this.dto.setSurnameUser(surnameUser);
        this.dto.setGmailUser(gmailUser);
        this.dto.setMessage(message);
    }

    // Método para imprimir el mensaje
    public void printMessage() {
        System.out.println("Mensaje: " + dto.getMessage());
    }
}
