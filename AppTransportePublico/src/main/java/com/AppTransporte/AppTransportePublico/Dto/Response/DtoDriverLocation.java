package com.AppTransporte.AppTransportePublico.Dto.Response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoDriverLocation {

    private String idLocation;

    private DtoUser user;

    private double latitude;

    private double longitude;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp;
}
