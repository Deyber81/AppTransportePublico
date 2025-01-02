package com.AppTransporte.AppTransportePublico.Dto.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoTrip {

    private String idTrip;

    private DtoUser user;

    private DtoRoute route;

    private DtoVehicleSchedule schedule;

    private String tripDate;

    private String startTime;

    private String endTime;

    private double totalPenaltyAmount;
}
