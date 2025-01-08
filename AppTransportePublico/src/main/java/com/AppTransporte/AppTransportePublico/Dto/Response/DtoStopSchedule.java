package com.AppTransporte.AppTransportePublico.Dto.Response;
import lombok.Data;

@Data
public class DtoStopSchedule {

    private String idStopSchedule;

    private String idStop;

    private String idSchedule;

    private String plannedArrivalTime;

    private String plannedDepartureTime;
}
