package com.AppTransporte.AppTransportePublico.Dto.Requets;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestTrip {

    @NotBlank(message = "El campo 'IdUser' es requerido.")
    private String idUser;

    @NotBlank(message = "El campo 'IdRoute' es requerido.")
    private String idRoute;

    @NotBlank(message = "El campo 'IdSchedule' es requerido.")
    private String idSchedule;

    @NotBlank(message = "El campo 'TripDate' es requerido.")
    private String tripDate;  // Fecha del viaje

    @NotBlank(message = "El campo 'StartTime' es requerido.")
    private String startTime;

    @NotBlank(message = "El campo 'EndTime' es requerido.")
    private String endTime;

    private Double totalPenaltyAmount = 0.00;

    // Relación uno a muchos con los registros de paradas
    private List<RequestStopRecord> stopRecords;  // Lista de registros de paradas realizadas
}
