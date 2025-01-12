package AppTansporte.AppTransportepublico.Business;

import AppTansporte.AppTransportepublico.Dto.Response.DtoRoute;
import AppTansporte.AppTransportepublico.Entity.TRoute;
import AppTansporte.AppTransportepublico.Repository.RepoRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RouteBusiness {

    @Autowired
    private RepoRoute repoRoute;

    // Validar si la ruta existe
    public void validateRouteExistence(String idRoute, List<String> errorMessages) {
        boolean exists = repoRoute.existsById(idRoute);
        if (!exists) {
            errorMessages.add("La ruta con el ID especificado no existe.");
        }
    }

    // Validar unicidad del nombre de la ruta
    public void validateRouteNameUniqueness(String name, String idRoute, List<String> errorMessages) {
        // Verificar si el nombre de la ruta ya está en uso
        List<TRoute> existingRoutes = repoRoute.findByName(name);

        // Validar unicidad solo si el nombre ha cambiado (comparar por ID)
        if (!existingRoutes.isEmpty() && existingRoutes.stream().anyMatch(route -> !route.getIdRoute().equals(idRoute))) {
            errorMessages.add("Ya existe una ruta con el nombre especificado.");
        }
    }

    // Actualizar los campos de la ruta
    public void updateRouteFields(DtoRoute dtoRoute, TRoute route) {
        if (dtoRoute.getName() != null) route.setName(dtoRoute.getName());
        if (dtoRoute.getDescription() != null) route.setDescription(dtoRoute.getDescription());
        if (dtoRoute.getStartLatitude() != null) route.setStartLatitude(dtoRoute.getStartLatitude());
        if (dtoRoute.getStartLongitude() != null) route.setStartLongitude(dtoRoute.getStartLongitude());
        if (dtoRoute.getEndLatitude() != null) route.setEndLatitude(dtoRoute.getEndLatitude());
        if (dtoRoute.getEndLongitude() != null) route.setEndLongitude(dtoRoute.getEndLongitude());
        if (dtoRoute.getOperationalStartTime() != null) route.setOperationalStartTime(dtoRoute.getOperationalStartTime());
        if (dtoRoute.getOperationalEndTime() != null) route.setOperationalEndTime(dtoRoute.getOperationalEndTime());
        if (dtoRoute.getDelayPenaltyAmount() != null) route.setDelayPenaltyAmount(dtoRoute.getDelayPenaltyAmount());
        if (dtoRoute.getBreakInterval() != null) route.setBreakInterval(dtoRoute.getBreakInterval());
        if (dtoRoute.getRouteDuration() != null) route.setRouteDuration(dtoRoute.getRouteDuration());
    }
    
    public void validateRouteTimes(DtoRoute dtoRoute, List<String> errorMessages) {
        if (dtoRoute.getOperationalStartTime() != null && dtoRoute.getOperationalEndTime() != null) {
            if (dtoRoute.getOperationalEndTime().isBefore(dtoRoute.getOperationalStartTime())) {
                errorMessages.add("La hora de finalización no puede ser menor que la hora de inicio.");
            }
        } else {
            errorMessages.add("Las horas de inicio y fin son obligatorias.");
        }
    }
}

