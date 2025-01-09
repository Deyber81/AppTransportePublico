package AppTansporte.AppTransportepublico.Business;

import AppTansporte.AppTransportepublico.Repository.RepoUser;
import AppTansporte.AppTransportepublico.Repository.RepoVehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VehicleBusiness {

    @Autowired
    private RepoVehicle repoVehicle;
    @Autowired
    private RepoUser repoUser;
    
    public void validateLicensePlateUniqueness(String licensePlate, List<String> errorMessages) {
        if (repoVehicle.existsByLicensePlate(licensePlate)) {
            errorMessages.add("La placa del vehículo '" + licensePlate + "' ya está registrada.");
        }
    }

    public void validateUserExistence(String idUser, List<String> errorMessages) {
        if (!repoUser.existsById(idUser)) {
            errorMessages.add("El usuario con el ID '" + idUser + "' no existe.");
        }
    }

}
