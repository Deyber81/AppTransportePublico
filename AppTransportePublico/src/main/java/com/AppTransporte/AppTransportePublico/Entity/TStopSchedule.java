package com.AppTransporte.AppTransportePublico.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TStopSchedule")
public class TStopSchedule {

    @Id
    @Column(nullable = false, length = 36)
    private String idStopSchedule;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "idStop", nullable = false)
    private TStop stop;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "idSchedule", nullable = false)
    private TVehicleSchedule schedule;

    @NotNull
    @Column(nullable = false)
    private String plannedArrivalTime;

    @NotNull
    @Column(nullable = false)
    private String plannedDepartureTime;
}
