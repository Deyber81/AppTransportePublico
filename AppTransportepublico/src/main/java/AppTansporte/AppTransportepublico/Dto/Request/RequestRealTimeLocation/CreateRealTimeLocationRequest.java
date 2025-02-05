package AppTansporte.AppTransportepublico.Dto.Request.RequestRealTimeLocation;
import lombok.Data;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class CreateRealTimeLocationRequest {

    @NotNull(message = "El IdLocation es obligatorio")
    @Size(min = 36, max = 36, message = "El IdLocation debe ser un UUID válido con 36 caracteres")
    private String idLocation;

    @NotNull(message = "El IdVehicle es obligatorio")
    @Size(min = 36, max = 36, message = "El IdVehicle debe ser un UUID válido con 36 caracteres")
    private String idVehicle;

    @NotNull(message = "La Latitude es obligatoria")
    @DecimalMin(value = "-90.0", message = "La Latitude debe estar entre -90.0 y 90.0")
    @DecimalMax(value = "90.0", message = "La Latitude debe estar entre -90.0 y 90.0")
    private Double latitude;

    @NotNull(message = "La Longitude es obligatoria")
    @DecimalMin(value = "-180.0", message = "La Longitude debe estar entre -180.0 y 180.0")
    @DecimalMax(value = "180.0", message = "La Longitude debe estar entre -180.0 y 180.0")
    private Double longitude;

    @NotNull(message = "El Timestamp es obligatorio")
    private LocalDateTime timestamp;
}
