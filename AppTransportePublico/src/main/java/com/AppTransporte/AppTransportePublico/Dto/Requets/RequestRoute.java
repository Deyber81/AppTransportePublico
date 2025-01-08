package com.AppTransporte.AppTransportePublico.Dto.Requets;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestRoute {

    @NotBlank(message = "El campo 'Name' es requerido.")
    @Size(max = 100, message = "El campo 'Name' no debe exceder los 100 caracteres.")
    private String name;

    @Size(max = 255, message = "El campo 'Description' no debe exceder los 255 caracteres.")
    private String description;

    @NotNull(message = "La 'StartLatitude' es requerida.")
    private Double startLatitude;

    @NotNull(message = "La 'StartLongitude' es requerida.")
    private Double startLongitude;

    @NotNull(message = "La 'EndLatitude' es requerida.")
    private Double endLatitude;

    @NotNull(message = "La 'EndLongitude' es requerida.")
    private Double endLongitude;
}
