package com.AppTransporte.AppTransportePublico.Bussines;
import com.AppTransporte.AppTransportePublico.Entity.TVehicle;
import com.AppTransporte.AppTransportePublico.Repository.RepoVehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class VehicleBusiness {

    @Autowired
    private RepoVehicle repoVehicle;

    /**
     * Valida si una placa ya está registrada en la base de datos.
     * 
     * @param licensePlate La placa a verificar.
     * @param idVehicle El ID del vehículo actual (para exclusión en actualizaciones).
     * @param errors Lista de errores a completar si la placa ya está registrada.
     */
    public void validateLicensePlate(String licensePlate, String idVehicle, List<String> errors) {
        Optional<TVehicle> existingVehicle = repoVehicle.findByLicensePlate(licensePlate);
        if (existingVehicle.isPresent() && !existingVehicle.get().getIdVehicle().equals(idVehicle)) {
            errors.add("La placa '" + licensePlate + "' ya está registrada.");
        }
    }
}
