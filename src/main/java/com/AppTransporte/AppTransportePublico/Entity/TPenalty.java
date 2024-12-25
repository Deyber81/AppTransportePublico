package com.AppTransporte.AppTransportePublico.Entity;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TPenalty")
@Getter
@Setter
public class TPenalty implements Serializable {

    @Id
    @Column(name = "IdPenalty", nullable = false)
    private String idPenalty;

    @ManyToOne
    @JoinColumn(name = "IdTrip", nullable = false)
    private TTrip trip; // Relación con TTrip

    @Column(name = "PenaltyReason", nullable = false)
    private String penaltyReason;

    @Column(name = "PenaltyAmount", nullable = false)
    private double penaltyAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PenaltyDate", nullable = false)
    private Date penaltyDate;
}
