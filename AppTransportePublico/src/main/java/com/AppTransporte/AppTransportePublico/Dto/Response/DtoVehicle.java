package com.AppTransporte.AppTransportePublico.Dto.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoVehicle {

    private String idVehicle;

    private String licensePlate;

    private String model;

    private int capacity;

    private DtoUser user;
}

