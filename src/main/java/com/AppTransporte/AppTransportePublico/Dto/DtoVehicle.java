package com.AppTransporte.AppTransportePublico.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoVehicle {

    private String idVehicle;

    private String idUser;

    private String licensePlate;

    private String model;

    private int capacity;
}

