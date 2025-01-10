package AppTansporte.AppTransportepublico.Dto.Request.RequetsStop;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class CreateStopRequest {
     @NotNull(message = "El ID de la ruta es obligatorio.")
    @Size(min = 0, max = 36, message = "El ID de la ruta debe tener entre 0 y 36 caracteres")
    private String idRoute;

    @NotNull(message = "El nombre de la parada es obligatorio.")
    @Size(min = 0, max = 100, message = "El nombre de la parada debe tener entre 0 y 100 caracteres")
    private String stopName;

    @NotNull(message = "La latitud de la parada es obligatoria.")
    @DecimalMin(value = "-180.0", message = "La latitud mínima es -180.")
    @DecimalMax(value = "180.0", message = "La latitud máxima es 180.")
    private Double latitude;

    @NotNull(message = "La longitud de la parada es obligatoria.")
    @DecimalMin(value = "-180.0", message = "La longitud mínima es -180.")
    @DecimalMax(value = "180.0", message = "La longitud máxima es 180.")
    private Double longitude;

    @NotNull(message = "El número de la parada es obligatorio.")
    private int stopOrder;

    @NotNull(message = "La duración de la parada es obligatoria.")
    @DecimalMin(value = "1", message = "La duración mínima de la parada es 1 minuto.")
    private int stopDuration; // Duración de la parada en minutos
}
