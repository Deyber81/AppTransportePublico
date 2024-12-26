package com.AppTransporte.AppTransportePublico.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "TTrip")
@Getter
@Setter
public class TTrip implements Serializable {

    @Id
    @Column(name = "IdTrip", nullable = false)
    private String idTrip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdUser", nullable = false)
    private TUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdRoute", nullable = false)
    private TRoute route;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdSchedule", nullable = false)
    private TVehicleSchedule schedule;

    @Column(name = "TripDate", nullable = false)
    private String tripDate;

    @Column(name = "StartTime", nullable = false)
    private String startTime;

    @Column(name = "EndTime", nullable = false)
    private String endTime;

    @Column(name = "TotalPenaltyAmount", nullable = false, columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private double totalPenaltyAmount;
}
