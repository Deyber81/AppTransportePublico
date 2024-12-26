package com.AppTransporte.AppTransportePublico.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoVehicleSchedule {

    private String idSchedule;

    private DtoVehicle vehicle;

    private String scheduleDate;

    private String departureTime;

    private String arrivalTime;
}
