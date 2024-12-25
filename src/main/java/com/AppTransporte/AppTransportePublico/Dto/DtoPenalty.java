package com.AppTransporte.AppTransportePublico.Dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoPenalty {

    private String idPenalty;
    private String idTrip;
    private String penaltyReason;
    private double penaltyAmount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date penaltyDate;
}
