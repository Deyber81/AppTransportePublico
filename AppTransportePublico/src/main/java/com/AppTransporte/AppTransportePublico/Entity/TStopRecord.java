package com.AppTransporte.AppTransportePublico.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TStopRecord")
@Getter
@Setter
public class TStopRecord implements Serializable {

    @Id
    @Column(name = "IdStopRecord", nullable = false)
    private String idStopRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdStop", nullable = false)
    private TStop stop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdTrip", nullable = false)
    private TTrip trip;

    @Column(name = "ActualArrivalTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualArrivalTime;
}
