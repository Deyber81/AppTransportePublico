package AppTansporte.AppTransportepublico.Dto.Response;

import java.time.LocalTime;
import lombok.Data;

@Data
public class DtoRoute {

    private String idRoute;

    private String name;

    private String description;

    private Double startLatitude;

    private Double startLongitude;

    private Double endLatitude;

    private Double endLongitude;

    private LocalTime operationalStartTime;

    private LocalTime operationalEndTime;

    private Double delayPenaltyAmount;

    private Integer breakInterval;

    private Integer routeDuration;
}