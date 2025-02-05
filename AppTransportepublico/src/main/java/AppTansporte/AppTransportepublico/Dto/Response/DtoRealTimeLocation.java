package AppTansporte.AppTransportepublico.Dto.Response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DtoRealTimeLocation {
    private String idLocation;
    private String idVehicle;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDateTime timestamp;
}
