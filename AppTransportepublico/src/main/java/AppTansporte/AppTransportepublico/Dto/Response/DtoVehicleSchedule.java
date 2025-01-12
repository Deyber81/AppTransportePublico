package AppTansporte.AppTransportepublico.Dto.Response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoVehicleSchedule {
    private String idSchedule;
    private String idVehicle;
    private String idRoute;
    private String dayOfWeek;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private String direction;
}
