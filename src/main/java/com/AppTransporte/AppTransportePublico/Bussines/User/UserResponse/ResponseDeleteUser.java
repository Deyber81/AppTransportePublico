package com.AppTransporte.AppTransportePublico.Bussines.User.UserResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDeleteUser extends com.AppTransporte.AppTransportePublico.Bussines.ResponseGeneral {

    // Clase interna para los detalles de la respuesta
    @Getter
    @Setter
    public static class Dto {
        private String userId;  // ID del usuario eliminado
        private String message;  // Mensaje de confirmación (exitoso o error)
    }

    private Dto dto;

    public ResponseDeleteUser() {
        this.dto = new Dto();
    }

    // Método para establecer datos del mensaje y del ID
    public void setDeleteData(String userId, String message) {
        this.dto.setUserId(userId);
        this.dto.setMessage(message);
    }

    // Método para imprimir el mensaje
    public void printMessage() {
        System.out.println("Mensaje: " + dto.getMessage());
    }
}
