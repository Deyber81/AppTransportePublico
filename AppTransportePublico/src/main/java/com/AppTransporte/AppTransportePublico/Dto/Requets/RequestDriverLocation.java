package com.AppTransporte.AppTransportePublico.Dto.Requets;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDriverLocation {

    @NotBlank(message = "El campo 'IdUser' es requerido.")
    private String idUser;

    @NotNull(message = "La 'Latitude' es requerida.")
    private Double latitude;

    @NotNull(message = "La 'Longitude' es requerida.")
    private Double longitude;

    @NotNull(message = "El campo 'Timestamp' es requerido.")
    private String timestamp;  // Marca temporal
}
