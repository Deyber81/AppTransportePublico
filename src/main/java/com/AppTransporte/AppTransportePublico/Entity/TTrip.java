package com.AppTransporte.AppTransportePublico.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TTrip")
@Getter
@Setter
public class TTrip implements Serializable {

    @Id
    @Column(name = "IdTrip", nullable = false)
    private String idTrip;

    @ManyToOne
    @JoinColumn(name = "IdUser", nullable = false)
    private TUser user; // Relación con TUser

    @ManyToOne
    @JoinColumn(name = "IdRoute", nullable = false)
    private TRoute route; // Relación con TRoute

    @Temporal(TemporalType.DATE)
    @Column(name = "TripDate", nullable = false)
    private Date tripDate;

    @Column(name = "StartTime", nullable = false)
    private String startTime;

    @Column(name = "EndTime", nullable = false)
    private String endTime;

    @Column(name = "TotalPenaltyAmount", nullable = false)
    private double totalPenaltyAmount = 0.0;

    // Relación Uno a Muchos con TStopRecord
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TStopRecord> stopRecords;

    // Relación Uno a Muchos con TPenalty
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TPenalty> penalties;
}