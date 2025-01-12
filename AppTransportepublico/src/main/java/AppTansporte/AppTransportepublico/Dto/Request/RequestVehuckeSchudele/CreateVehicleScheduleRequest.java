package AppTansporte.AppTransportepublico.Dto.Request.RequestVehuckeSchudele;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateVehicleScheduleRequest {

    @NotBlank(message = "El ID del vehículo es obligatorio.")
    private String idVehicle;

    @NotBlank(message = "El ID de la ruta es obligatorio.")
    private String idRoute;

    @NotBlank(message = "El día de la semana es obligatorio.")
    @Pattern(regexp = "^(Lunes|Martes|Miércoles|Jueves|Viernes|Sábado|Domingo)$", message = "El día de la semana debe ser válido.")
    private String dayOfWeek;

    @NotNull(message = "La hora de salida es obligatoria.")
    private LocalTime departureTime;
}

