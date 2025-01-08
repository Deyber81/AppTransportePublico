package com.AppTransporte.AppTransportePublico.Dto.Requets;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestVehicle {

    @NotBlank(message = "El campo 'LicensePlate' es requerido.")
    @Size(max = 50, message = "El campo 'LicensePlate' no debe exceder los 50 caracteres.")
    private String licensePlate;

    @NotBlank(message = "El campo 'Model' es requerido.")
    @Size(max = 100, message = "El campo 'Model' no debe exceder los 100 caracteres.")
    private String model;

    @NotNull(message = "El campo 'Capacity' es requerido.")
    private int capacity;

    @NotBlank(message = "El campo 'IdUser' es requerido.")
    private String idUser;
}
