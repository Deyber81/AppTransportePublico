package com.AppTransporte.AppTransportePublico.Controller.Requets;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestVehicleSchedule {

    @NotBlank(message = "El campo 'IdVehicle' es requerido.")
    private String idVehicle;

    @NotNull(message = "El campo 'ScheduleDate' es requerido.")
    private Date scheduleDate;

    @NotNull(message = "El campo 'DepartureTime' es requerido.")
    private LocalTime departureTime;

    @NotNull(message = "El campo 'ArrivalTime' es requerido.")
    private LocalTime arrivalTime;
}
