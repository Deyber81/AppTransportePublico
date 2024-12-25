package com.AppTransporte.AppTransportePublico.Dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoTrip {

    private String idTrip;
    private String idUser;
    private String idRoute;
    private Date tripDate;
    private String startTime;
    private String endTime;
    private double totalPenaltyAmount;
}
