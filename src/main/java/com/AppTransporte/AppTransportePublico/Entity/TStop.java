package com.AppTransporte.AppTransportePublico.Entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TStop")
@Getter
@Setter
public class TStop implements Serializable {

    @Id
    @Column(name = "IdStop", nullable = false)
    private String idStop;

    @ManyToOne
    @JoinColumn(name = "IdRoute", nullable = false)
    private TRoute route; // Relación con TRoute

    @Column(name = "StopName", nullable = false)
    private String stopName;

    @Column(name = "Latitude", nullable = false)
    private double latitude;

    @Column(name = "Longitude", nullable = false)
    private double longitude;

    @Column(name = "StopTime", nullable = false)
    private String stopTime;
}

