package com.AppTransporte.AppTransportePublico.Entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TVehicle")
@Getter
@Setter
public class TVehicle implements Serializable {

    @Id
    @Column(name = "IdVehicle", nullable = false)
    private String idVehicle;

    @ManyToOne
    @JoinColumn(name = "IdUser", nullable = false)
    private TUser user; // Relación con TUser

    @Column(name = "LicensePlate", nullable = false, unique = true)
    private String licensePlate;

    @Column(name = "Model", nullable = false)
    private String model;

    @Column(name = "Capacity", nullable = false)
    private int capacity;
}