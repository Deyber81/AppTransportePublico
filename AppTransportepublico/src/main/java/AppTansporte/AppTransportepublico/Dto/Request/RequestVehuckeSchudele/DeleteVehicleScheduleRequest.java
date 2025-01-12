package AppTansporte.AppTransportepublico.Dto.Request.RequestVehuckeSchudele;

import jakarta.validation.constraints.NotBlank;

public class DeleteVehicleScheduleRequest  {
    @NotBlank(message = "El ID del vehículo es obligatorio.")
    private String idVehicle;

    @NotBlank(message = "El día de la semana es obligatorio.")
    private String dayOfWeek;
}
