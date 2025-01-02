package com.AppTransporte.AppTransportePublico.Dto.Response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoStopRecord {

    private String idStopRecord;

    private DtoStop stop;

    private DtoTrip trip;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date actualArrivalTime;
}
