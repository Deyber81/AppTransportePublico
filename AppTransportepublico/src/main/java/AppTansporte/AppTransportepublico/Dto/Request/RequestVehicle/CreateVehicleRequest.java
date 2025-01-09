package AppTansporte.AppTransportepublico.Dto.Request.RequestVehicle;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateVehicleRequest {

    @NotNull(message = "El ID del usuario es obligatorio.")
    private String idUser;

    @NotBlank(message = "La placa del vehículo es obligatoria.")
    @Size(max = 50, message = "La placa no puede tener más de 50 caracteres.")
    private String licensePlate;

    @NotBlank(message = "El modelo del vehículo es obligatorio.")
    @Size(max = 100, message = "El modelo no puede tener más de 100 caracteres.")
    private String model;

    @Min(value = 1, message = "La capacidad debe ser al menos 1.")
    private int capacity;
}
