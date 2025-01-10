package AppTansporte.AppTransportepublico.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TStop")
public class TStop {

    @Id
    @Column(name = "IdStop")
    private String idStop;

    @Column(name = "IdRoute", nullable = false)
    private String idRoute;

    @Column(name = "StopName", nullable = false)
    private String stopName;

    @Column(name = "Latitude", nullable = false)
    private Double latitude;

    @Column(name = "Longitude", nullable = false)
    private Double longitude;

    @Column(name = "StopOrder", nullable = false)
    private int stopOrder;

    @Column(name = "StopDuration", nullable = false)
    private int stopDuration; 
    
    @ManyToOne
    @JoinColumn(name = "IdRoute", insertable = false, updatable = false) 
    private TRoute route;

}