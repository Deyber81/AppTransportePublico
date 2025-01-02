package com.AppTransporte.AppTransportePublico.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TPenalty")
@Getter
@Setter
public class TPenalty implements Serializable {

    @Id
    @Column(name = "IdPenalty", nullable = false)
    private String idPenalty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdTrip", nullable = false)
    private TTrip trip;

    @Column(name = "PenaltyReason", nullable = false)
    private String penaltyReason;

    @Column(name = "PenaltyAmount", nullable = false)
    private double penaltyAmount;

    @Column(name = "PenaltyDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date penaltyDate;
}
