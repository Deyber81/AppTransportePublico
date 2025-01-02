package com.AppTransporte.AppTransportePublico.Dto.Requets;

import java.sql.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestPenalty {

    @NotBlank(message = "El campo 'IdTrip' es requerido.")
    private String idTrip;

    @NotBlank(message = "El campo 'PenaltyReason' es requerido.")
    @Size(max = 255, message = "El campo 'PenaltyReason' no debe exceder los 255 caracteres.")
    private String penaltyReason;

    @NotNull(message = "El campo 'PenaltyAmount' es requerido.")
    private double penaltyAmount;

    @NotNull(message = "El campo 'PenaltyDate' es requerido.")
    private Date penaltyDate;
}
