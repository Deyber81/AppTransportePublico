package com.AppTransporte.AppTransportePublico.Dto.Response;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class DtoRealTimeNotification {

    private String idNotification;

    private String idVehicle;

    private String notificationType;

    private String message;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp;

    private Boolean isRead;
}
