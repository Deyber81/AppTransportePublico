package AppTansporte.AppTransportepublico.Dto;

import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ResponseGeneral {

    private String message;
    private List<String> errors;
    private HttpStatus statusCode;
    private Object data;
    private Map<String, Object> extra = new HashMap<>(); // Campo extra para datos adicionales

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

    public void addExtra(String key, Object value) {
        this.extra.put(key, value); // Añadir un dato al mapa extra
    }

    public Object getExtraValue(String key) {
        return this.extra.get(key); // Obtener un valor específico del mapa extra
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

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }
}
