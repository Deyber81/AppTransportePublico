package com.AppTransporte.AppTransportePublico.Entity;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TRoute")
@Getter
@Setter
public class TRoute implements Serializable {

    @Id
    @Column(name = "IdRoute", nullable = false)
    private String idRoute;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "StartLatitude", nullable = false)
    private double startLatitude;

    @Column(name = "StartLongitude", nullable = false)
    private double startLongitude;

    @Column(name = "EndLatitude", nullable = false)
    private double endLatitude;

    @Column(name = "EndLongitude", nullable = false)
    private double endLongitude;

    // Relación Uno a Muchos con TStop
    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TStop> stops;
}
