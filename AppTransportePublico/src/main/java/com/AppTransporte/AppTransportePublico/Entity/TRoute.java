package com.AppTransporte.AppTransportePublico.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "TRoute")
@Getter
@Setter
public class TRoute implements Serializable {

    @Id
    @Column(name = "IdRoute", nullable = false, updatable = false)
    private String idRoute;

    @Column(name = "Name", nullable = false, length = 100)
    private String name;

    @Column(name = "Description", length = 255)
    private String description;

    @Column(name = "StartLatitude", nullable = false, precision = 9, scale = 6)
    private double startLatitude;

    @Column(name = "StartLongitude", nullable = false, precision = 9, scale = 6)
    private double startLongitude;

    @Column(name = "EndLatitude", nullable = false, precision = 9, scale = 6)
    private double endLatitude;

    @Column(name = "EndLongitude", nullable = false, precision = 9, scale = 6)
    private double endLongitude;
}
