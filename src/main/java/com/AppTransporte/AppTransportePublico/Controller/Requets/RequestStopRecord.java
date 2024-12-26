package com.AppTransporte.AppTransportePublico.Controller.Requets;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestStopRecord {

    @NotBlank(message = "El campo 'IdStop' es requerido.")
    private String idStop;

    @NotBlank(message = "El campo 'IdTrip' es requerido.")
    private String idTrip;

    @NotNull(message = "El campo 'ActualArrivalTime' es requerido.")
    private String actualArrivalTime;  // Hora de llegada real
}
