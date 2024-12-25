package com.AppTransporte.AppTransportePublico.Entity;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TStopRecord")
@Getter
@Setter
public class TStopRecord implements Serializable {

    @Id
    @Column(name = "IdStopRecord", nullable = false)
    private String idStopRecord;

    @ManyToOne
    @JoinColumn(name = "IdStop", nullable = false)
    private TStop stop; // Relación con TStop

    @ManyToOne
    @JoinColumn(name = "IdTrip", nullable = false)
    private TTrip trip; // Relación con TTrip

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ActualArrivalTime", nullable = false)
    private Date actualArrivalTime;
}
