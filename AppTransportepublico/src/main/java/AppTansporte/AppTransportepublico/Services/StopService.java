package AppTansporte.AppTransportepublico.Services;

import AppTansporte.AppTransportepublico.Dto.ResponseGeneral;
import AppTansporte.AppTransportepublico.Dto.Response.DtoStop;
import AppTansporte.AppTransportepublico.Entity.TStop;
import AppTansporte.AppTransportepublico.Business.GenericMapper;
import AppTansporte.AppTransportepublico.Business.StopBusiness;
import AppTansporte.AppTransportepublico.Repository.RepoStop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StopService {

    @Autowired
    private RepoStop repoStop;

    @Autowired
    private GenericMapper genericMapper;

    @Autowired
    private StopBusiness stopBusiness;

    public ResponseGeneral insetStop(DtoStop dtoStop) {
        ResponseGeneral response = new ResponseGeneral();

        // Verificar si la ruta existe
        if (!stopBusiness.checkIfRouteExists(dtoStop.getIdRoute())) {
            response.error("La ruta con el ID especificado no existe.");
            return response;
        }

        // Verificar si el `StopOrder` ya existe y reordenar las paradas
        stopBusiness.reordenarParadas(dtoStop.getIdRoute(), dtoStop.getStopOrder());

        // Generar un ID único para la nueva parada
        dtoStop.setIdStop(UUID.randomUUID().toString());

        // Convertir el DTO a la entidad TStop
        TStop tStop = genericMapper.map(dtoStop, TStop.class);

        // Guardar la nueva parada
        repoStop.save(tStop);

        response.success("Parada agregada exitosamente.");
        response.setData(dtoStop);
        return response;
    }

    public ResponseGeneral updateStop(DtoStop dtoStop) {
        ResponseGeneral response = new ResponseGeneral();

        // Verificar si la parada existe
        if (!stopBusiness.checkIfStopExists(dtoStop.getIdStop())) {
            response.error("La parada con el ID especificado no existe.");
            return response;
        }

        // Verificar si la ruta existe
        if (!stopBusiness.checkIfRouteExists(dtoStop.getIdRoute())) {
            response.error("La ruta con el ID especificado no existe.");
            return response;
        }

        Optional<TStop> optionalStop = repoStop.findById(dtoStop.getIdStop());
        TStop existingStop = optionalStop.get();

        // Reordenar las paradas si es necesario
        stopBusiness.reordenarParadas(existingStop.getIdRoute(), dtoStop.getStopOrder());

        // Actualizar los valores de la parada
        stopBusiness.actualizarParada(existingStop, dtoStop);

        // Guardar la parada actualizada
        repoStop.save(existingStop);

        response.success("Parada actualizada exitosamente.");
        return response;
    }

    public ResponseGeneral getStopById(String idStop) {
        ResponseGeneral response = new ResponseGeneral();

        Optional<TStop> stopOptional = repoStop.findById(idStop);
        if (stopOptional.isEmpty()) {
            response.error("Parada no encontrada.");
            return response;
        }

        // Convertir la entidad a DTO
        DtoStop dtoStop = genericMapper.map(stopOptional.get(), DtoStop.class);

        response.success("Parada encontrada.");
        response.setData(dtoStop);
        return response;
    }

    public List<DtoStop> listAllStops() {
        List<TStop> stops = repoStop.findAll();
        return stops.stream()
            .map(stop -> genericMapper.map(stop, DtoStop.class))
            .collect(Collectors.toList());
    }

    public ResponseGeneral deleteStop(String idStop) {
        ResponseGeneral response = new ResponseGeneral();

        Optional<TStop> stopOptional = repoStop.findById(idStop);
        if (stopOptional.isEmpty()) {
            response.error("La parada con el ID especificado no existe.");
            return response;
        }

        TStop stopToDelete = stopOptional.get();
        String idRoute = stopToDelete.getIdRoute();
        int stopOrderToDelete = stopToDelete.getStopOrder();

        // Eliminar la parada y reordenar
        stopBusiness.eliminarYReordenarParadas(idStop, idRoute, stopOrderToDelete);

        response.success("Parada eliminada y reordenada exitosamente.");
        return response;
    }
}
