package com.AppTransporte.AppTransportePublico.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "TVehicleSchedule")
@Getter
@Setter
public class TVehicleSchedule implements Serializable {

    @Id
    @Column(name = "IdSchedule", nullable = false)
    private String idSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdVehicle", nullable = false)
    private TVehicle vehicle;

    @Column(name = "ScheduleDate", nullable = false)
    private String scheduleDate;

    @Column(name = "DepartureTime", nullable = false)
    private String departureTime;

    @Column(name = "ArrivalTime", nullable = false)
    private String arrivalTime;
}
