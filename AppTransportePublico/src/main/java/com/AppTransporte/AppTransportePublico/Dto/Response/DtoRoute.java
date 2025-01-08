package com.AppTransporte.AppTransportePublico.Dto.Response;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class DtoRoute {

    private String idRoute;

    private String name;

    private String description;

    private double startLatitude;

    private double startLongitude;

    private double endLatitude;

    private double endLongitude;
}
