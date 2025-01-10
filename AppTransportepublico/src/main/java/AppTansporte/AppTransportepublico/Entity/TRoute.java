package AppTansporte.AppTransportepublico.Entity;

import java.time.LocalTime;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TRoute")
public class TRoute {

    @Id
    @Column(name = "IdRoute", nullable = false, unique = true, length = 36)
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

    @Column(name = "DelayPenaltyAmount", nullable = false, columnDefinition = "DECIMAL(10, 2) DEFAULT 0.00")
    private Double delayPenaltyAmount = 0.00;

    @Column(name = "BreakInterval", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer breakInterval = 0;

    @Column(name = "RouteDuration", nullable = false)
    private Integer routeDuration;
}

