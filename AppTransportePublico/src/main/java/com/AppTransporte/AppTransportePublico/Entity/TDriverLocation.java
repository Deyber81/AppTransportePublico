package com.AppTransporte.AppTransportePublico.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TDriverLocation")
@Getter
@Setter
public class TDriverLocation implements Serializable {

    @Id
    @Column(name = "IdLocation", nullable = false)
    private String idLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdUser", nullable = false)
    private TUser user;

    @Column(name = "Latitude", nullable = false)
    private double latitude;

    @Column(name = "Longitude", nullable = false)
    private double longitude;

    @Column(name = "Timestamp", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
}
