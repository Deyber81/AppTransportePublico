package AppTansporte.AppTransportepublico.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "TRealTimeLocation")
public class TRealTimeLocation {
    @Id
    @Column(name = "IdLocation")
    private String idLocation;

    @ManyToOne
    @JoinColumn(name = "IdVehicle", nullable = false)
    private TVehicle vehicle;

    @Column(name = "Latitude", nullable = false, precision = 9, scale = 6)
    private BigDecimal  latitude;

    @Column(name = "Longitude", nullable = false, precision = 9, scale = 6)
    private BigDecimal  longitude;

    @Column(name = "Timestamp", nullable = false)
    private LocalDateTime timestamp;
}
