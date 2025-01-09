package AppTansporte.AppTransportepublico.Business;

import AppTansporte.AppTransportepublico.Repository.RepoRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RouteBusiness {

    @Autowired
    private RepoRoute repoRoute;

    public void validateRouteNameUniqueness(String name, List<String> errorMessages) {
        if (repoRoute.existsByName(name)) {
            errorMessages.add("La ruta con el nombre '" + name + "' ya está registrada.");
        }
    }


    public void validateRouteExistence(String idRoute, List<String> errorMessages) {
        if (!repoRoute.existsById(idRoute)) {
            errorMessages.add("La ruta con el ID '" + idRoute + "' no existe.");
        }
    }

    public void validateCoordinates(Double latitude, Double longitude, List<String> errorMessages) {
        if (latitude != null && (latitude < -90 || latitude > 90)) {
            errorMessages.add("La latitud '" + latitude + "' debe estar entre -90 y 90.");
        }
        if (longitude != null && (longitude < -180 || longitude > 180)) {
            errorMessages.add("La longitud '" + longitude + "' debe estar entre -180 y 180.");
        }
    }
    public void validateRouteNameUniqueness(String name, String idRouteToExclude, List<String> errorMessages) {
        boolean exists = repoRoute.existsByNameAndIdRouteNot(name, idRouteToExclude);
        if (exists) {
            errorMessages.add("El nombre de la ruta ya está en uso por otra ruta.");
        }
    }
}
