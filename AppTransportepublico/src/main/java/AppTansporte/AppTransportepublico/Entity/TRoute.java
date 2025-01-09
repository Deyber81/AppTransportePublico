package AppTansporte.AppTransportepublico.Entity;
import java.time.LocalTime;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TRoute")
public class TRoute {

    @Id
    @Column(name = "IdRoute", nullable = false, unique = true)
    private String idRoute;

    @Column(name = "Name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "Description", length = 255)
    private String description;

    @Column(name = "StartLatitude", nullable = false)
    private Double startLatitude;
    
    @Column(name = "StartLongitude", nullable = false)
    private Double startLongitude;
    
    @Column(name = "EndLatitude", nullable = false)
    private Double endLatitude;
    
    @Column(name = "EndLongitude", nullable = false)
    private Double endLongitude;

    @Column(name = "OperationalStartTime", nullable = false)
    private LocalTime operationalStartTime;
    
    @Column(name = "OperationalEndTime", nullable = false)
    private LocalTime operationalEndTime;
    
}
