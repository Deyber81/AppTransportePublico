package com.AppTransporte.AppTransportePublico.Controller.Requets;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestStop {

    @NotBlank(message = "El campo 'nameStop' es requerido.")
    @Size(max = 100, message = "El campo 'nameStop' no debe exceder los 100 caracteres.")
    private String nameStop;

    private double latitude;
    private double longitude;

    @NotBlank(message = "El campo 'estimatedArrivalTime' es requerido.")
    private String estimatedArrivalTime;
    // Relación con la ruta
    private String idRoute;  // ID de la ruta a la que pertenece la parada
}
