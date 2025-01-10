package AppTansporte.AppTransportepublico.Business;

import AppTansporte.AppTransportepublico.Dto.Response.DtoStop;
import AppTansporte.AppTransportepublico.Entity.TStop;
import AppTansporte.AppTransportepublico.Repository.RepoStop;
import AppTansporte.AppTransportepublico.Repository.RepoRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StopBusiness {

    @Autowired
    private RepoStop repoStop;

    @Autowired
    private RepoRoute repoRoute;

    // Validar si una parada existe
    public boolean checkIfStopExists(String idStop) {
        Optional<TStop> stopOptional = repoStop.findById(idStop);
        return stopOptional.isPresent(); // Devuelve true si la parada existe
    }

    // Validar si una ruta existe
    public boolean checkIfRouteExists(String idRoute) {
        return repoRoute.existsById(idRoute);  // Devuelve true si la ruta existe
    }

    // Validar si el `StopOrder` ya existe en la misma ruta
    public boolean checkIfStopOrderExists(String idRoute, int stopOrder) {
        List<TStop> stops = repoStop.findByIdRouteOrderByStopOrder(idRoute);
        return stops.stream().anyMatch(stop -> stop.getStopOrder() == stopOrder); // Verifica si el StopOrder existe
    }

    // Reordenar las paradas cuando se agrega una nueva
    public void reordenarParadas(String idRoute, int stopOrder) {
        List<TStop> stops = repoStop.findByIdRouteOrderByStopOrder(idRoute);

        for (TStop stop : stops) {
            if (stop.getStopOrder() >= stopOrder) {
                stop.setStopOrder(stop.getStopOrder() + 1);  // Desplaza las paradas posteriores
                repoStop.save(stop);  // Guardamos las paradas reordenadas
            }
        }
    }

    // Actualizar los valores de la parada
    public void actualizarParada(TStop existingStop, DtoStop dtoStop) {
        if (dtoStop.getIdRoute() != null) existingStop.setIdRoute(dtoStop.getIdRoute());
        if (dtoStop.getStopName() != null) existingStop.setStopName(dtoStop.getStopName());
        if (dtoStop.getLatitude() != null) existingStop.setLatitude(dtoStop.getLatitude());
        if (dtoStop.getLongitude() != null) existingStop.setLongitude(dtoStop.getLongitude());
        if (dtoStop.getStopOrder() != -1) existingStop.setStopOrder(dtoStop.getStopOrder());
    }

    // Eliminar y reordenar paradas después de una eliminación
    public void eliminarYReordenarParadas(String idStop, String idRoute, int stopOrderToDelete) {
        // Eliminar la parada
        repoStop.deleteById(idStop);

        // Reordenar las paradas
        List<TStop> stops = repoStop.findByIdRouteOrderByStopOrder(idRoute);
        for (TStop stop : stops) {
            if (stop.getStopOrder() > stopOrderToDelete) {
                stop.setStopOrder(stop.getStopOrder() - 1);  // Reducir el StopOrder de las paradas posteriores
                repoStop.save(stop);
            }
        }
    }
}
