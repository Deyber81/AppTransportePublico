package com.AppTransporte.AppTransportePublico.Bussines;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseGeneral {
    private String type;
    private List<String> listMessage;

    public ResponseGeneral() {
        this.type = "error"; // Por defecto, la respuesta es de tipo 'error'
        this.listMessage = new ArrayList<>();
    }

    // Añade un mensaje a la lista
    public void addResponseMessage(String message) {
        this.listMessage.add(message);
    }

    // Define el tipo de respuesta como 'success'
    public void success(String message) {
        this.type = "success";
        this.listMessage.add(message);
    }

    // Define el tipo de respuesta como 'warning'
    public void warning(String message) {
        this.type = "warning";
        this.listMessage.add(message);
    }

    // Define el tipo de respuesta como 'error'
    public void error(String message) {
        this.type = "error";
        this.listMessage.add(message);
    }

    // Define el tipo de respuesta como 'exception'
    public void exception(String message) {
        this.type = "exception";
        this.listMessage.add(message);
    }
}
