package AppTansporte.AppTransportepublico.Services;

import AppTansporte.AppTransportepublico.Business.RouteBusiness;
import AppTansporte.AppTransportepublico.Dto.ResponseGeneral;
import AppTansporte.AppTransportepublico.Dto.Response.DtoRoute;
import AppTansporte.AppTransportepublico.Entity.TRoute;
import AppTansporte.AppTransportepublico.Repository.RepoRoute;
import AppTansporte.AppTransportepublico.Business.GenericMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RouteService {

    @Autowired
    private RepoRoute repoRoute;

    @Autowired
    private RouteBusiness routeBusiness;

    @Autowired
    private GenericMapper genericMapper;


    public ResponseGeneral insertRoute(DtoRoute dtoRoute) {
        ResponseGeneral response = new ResponseGeneral();
        List<String> errorMessages = new ArrayList<>();

        // Validar unicidad del nombre de la ruta
        routeBusiness.validateRouteNameUniqueness(dtoRoute.getName(), errorMessages);

        if (!errorMessages.isEmpty()) {
            response.error(errorMessages);
            return response;
        }

        // Mapear DTO a entidad y guardar
        TRoute route = genericMapper.map(dtoRoute, TRoute.class);
        route.setIdRoute(UUID.randomUUID().toString());
        repoRoute.save(route);

        response.success("Ruta guardada exitosamente.");
        response.setData(dtoRoute); // Opcional, devolver el DTO en la respuesta
        return response;
    }

    public ResponseGeneral updateRoute(DtoRoute dtoRoute) {
        ResponseGeneral response = new ResponseGeneral();
        List<String> errorMessages = new ArrayList<>();

        // Validar si la ruta existe
        routeBusiness.validateRouteExistence(dtoRoute.getIdRoute(), errorMessages);

        // Validar unicidad del nombre si se actualiza
        if (dtoRoute.getName() != null) {
            routeBusiness.validateRouteNameUniqueness(dtoRoute.getName(), errorMessages);
        }

        if (!errorMessages.isEmpty()) {
            response.error(errorMessages);
            return response;
        }

        // Buscar la ruta existente y actualizar
        Optional<TRoute> optionalRoute = repoRoute.findById(dtoRoute.getIdRoute());
        if (optionalRoute.isEmpty()) {
            response.error("La ruta con el ID especificado no existe.");
            return response;
        }
        // Validar unicidad del nombre si se actualiza
   
        TRoute route = optionalRoute.get();
       // Validar unicidad del nombre si se actualiza
        if (dtoRoute.getName() != null) {
            routeBusiness.validateRouteNameUniqueness(dtoRoute.getName(), dtoRoute.getIdRoute(), errorMessages);
        }
        if (dtoRoute.getDescription() != null) route.setDescription(dtoRoute.getDescription());
        if (dtoRoute.getStartLatitude() != null) route.setStartLatitude(dtoRoute.getStartLatitude());
        if (dtoRoute.getStartLongitude() != null) route.setStartLongitude(dtoRoute.getStartLongitude());
        if (dtoRoute.getEndLatitude() != null) route.setEndLatitude(dtoRoute.getEndLatitude());
        if (dtoRoute.getEndLongitude() != null) route.setEndLongitude(dtoRoute.getEndLongitude());
        if (dtoRoute.getOperationalStartTime() != null) route.setOperationalStartTime(dtoRoute.getOperationalStartTime());
        if (dtoRoute.getOperationalEndTime() != null) route.setOperationalEndTime(dtoRoute.getOperationalEndTime());

        repoRoute.save(route);

        response.success("Ruta actualizada exitosamente.");
        return response;
    }

    public ResponseGeneral deleteRouteById(String idRoute) {
        ResponseGeneral response = new ResponseGeneral();
        List<String> errorMessages = new ArrayList<>();

        // Validar si la ruta existe
        routeBusiness.validateRouteExistence(idRoute, errorMessages);

        if (!errorMessages.isEmpty()) {
            response.error(errorMessages);
            return response;
        }

        repoRoute.deleteById(idRoute);
        response.success("Ruta eliminada exitosamente.");
        return response;
    }


    public ResponseGeneral getRouteById(String idRoute) {
        ResponseGeneral response = new ResponseGeneral();

        Optional<TRoute> routeOptional = repoRoute.findById(idRoute);
        if (routeOptional.isEmpty()) {
            response.error("La ruta con el ID especificado no existe.");
            return response;
        }

        // Mapear Entidad a DTO
        DtoRoute dtoRoute = genericMapper.map(routeOptional.get(), DtoRoute.class);
        response.success("Ruta encontrada.");
        response.setData(dtoRoute);
        return response;
    }

    public List<DtoRoute> listAllRoutes() {
        return repoRoute.findAll().stream()
                .map(route -> genericMapper.map(route, DtoRoute.class))
                .collect(Collectors.toList());
    }
}
