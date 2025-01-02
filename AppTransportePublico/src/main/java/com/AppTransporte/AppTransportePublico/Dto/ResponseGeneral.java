package com.AppTransporte.AppTransportePublico.Dto;

import org.springframework.http.HttpStatus;
import java.util.List;

public class ResponseGeneral {

    private String message;
    private List<String> errors;
    private HttpStatus statusCode;
    private Object data; 
    public void success(String message) {
        this.message = message;
        this.statusCode = HttpStatus.OK;
    }

    public void error(String errorMessage) {
        this.errors = List.of(errorMessage);
        this.statusCode = HttpStatus.BAD_REQUEST;
    }

    public void error(List<String> errorMessages) {
        this.errors = errorMessages;
        this.statusCode = HttpStatus.BAD_REQUEST;
    }

    // Getters y Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
