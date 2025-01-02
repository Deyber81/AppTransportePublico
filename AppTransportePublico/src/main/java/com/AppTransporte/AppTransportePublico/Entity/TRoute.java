package com.AppTransporte.AppTransportePublico.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

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

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TStop> stops;
}
