package com.AppTransporte.AppTransportePublico.Dto.Requets;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
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

    private int capacity;

    // Relación con el usuario (chofer)
    private String idUser;  // ID del chofer

    // Relación con la ruta (opcional, puede no ser necesaria en todos los casos)
    private List<String> routeIds;  // IDs de las rutas a las que está asignado el vehículo
}
