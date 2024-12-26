package com.AppTransporte.AppTransportePublico.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoStop {

    private String idStop;

    private DtoRoute route;

    private String stopName;

    private double latitude;

    private double longitude;

    private String stopTime;
}
