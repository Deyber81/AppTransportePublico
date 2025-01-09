package AppTansporte.AppTransportepublico.Dto.Request.RequestRoute;


import java.time.LocalTime;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateRouteRequest {

    @NotBlank(message = "El nombre de la ruta es obligatorio.")
    @Size(max = 100, message = "El nombre de la ruta no puede tener más de 100 caracteres.")
    private String name;
    
    @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres.")
    private String description;

    @NotNull(message = "La latitud de inicio es obligatoria.")
    @DecimalMin(value = "-90.0", message = "La latitud mínima es -90.")
    @DecimalMax(value = "90.0", message = "La latitud máxima es 90.")
    private Double startLatitude;

    @NotNull(message = "La longitud de inicio es obligatoria.")
    @DecimalMin(value = "-180.0", message = "La longitud mínima es -180.")
    @DecimalMax(value = "180.0", message = "La longitud máxima es 180.")
    private Double startLongitude;

    @NotNull(message = "La latitud de fin es obligatoria.")
    @DecimalMin(value = "-90.0", message = "La latitud mínima es -90.")
    @DecimalMax(value = "90.0", message = "La latitud máxima es 90.")
    private Double endLatitude;

    @NotNull(message = "La longitud de fin es obligatoria.")
    @DecimalMin(value = "-180.0", message = "La longitud mínima es -180.")
    @DecimalMax(value = "180.0", message = "La longitud máxima es 180.")
    private Double endLongitude;

    @NotNull(message = "La hora inicial de trabajo de la ruta es obligatoria.")
    private LocalTime operationalStartTime;

    @NotNull(message = "La hora final del trabajo de la ruta es obligatoria.")
    private LocalTime operationalEndTime;
}
