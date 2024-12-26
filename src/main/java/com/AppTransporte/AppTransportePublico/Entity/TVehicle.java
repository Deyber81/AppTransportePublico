package com.AppTransporte.AppTransportePublico.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "TVehicle")
@Getter
@Setter
public class TVehicle implements Serializable {

    @Id
    @Column(name = "IdVehicle", nullable = false)
    private String idVehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdUser", nullable = false)
    private TUser user;

    @Column(name = "LicensePlate", nullable = false, unique = true)
    private String licensePlate;

    @Column(name = "Model", nullable = false)
    private String model;

    @Column(name = "Capacity", nullable = false)
    private int capacity;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TVehicleSchedule> schedules;

}
