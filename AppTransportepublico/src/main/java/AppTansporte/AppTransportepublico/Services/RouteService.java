package AppTansporte.AppTransportepublico.Services;

import AppTansporte.AppTransportepublico.Dto.ResponseGeneral;
import AppTansporte.AppTransportepublico.Dto.Response.DtoRoute;
import AppTansporte.AppTransportepublico.Entity.TRoute;
import AppTansporte.AppTransportepublico.Repository.RepoRoute;
import AppTansporte.AppTransportepublico.Business.RouteBusiness;
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
    
    // Insertar nueva ruta
    public ResponseGeneral insertRoute(DtoRoute dtoRoute) {
        ResponseGeneral response = new ResponseGeneral();
        List<String> errorMessages = new ArrayList<>();

        // Validar unicidad del nombre de la ruta
        routeBusiness.validateRouteNameUniqueness(dtoRoute.getName(), null, errorMessages);
        routeBusiness.validateRouteTimes(dtoRoute, errorMessages);
        if (!errorMessages.isEmpty()) {
            response.error(errorMessages);
            return response;
        }

        // Mapear DTO a entidad
        TRoute route = genericMapper.map(dtoRoute, TRoute.class);
        route.setIdRoute(UUID.randomUUID().toString());
        repoRoute.save(route);

        response.success("Ruta guardada exitosamente.");
        response.setData(dtoRoute);
        return response;
    }

    // Actualizar ruta
    public ResponseGeneral updateRoute(DtoRoute dtoRoute) {
        ResponseGeneral response = new ResponseGeneral();
        List<String> errorMessages = new ArrayList<>();

        // Validar existencia de la ruta
        routeBusiness.validateRouteExistence(dtoRoute.getIdRoute(), errorMessages);
        routeBusiness.validateRouteTimes(dtoRoute, errorMessages);
        // Si el nombre ha cambiado, validar su unicidad
        if (dtoRoute.getName() != null) {
            routeBusiness.validateRouteNameUniqueness(dtoRoute.getName(), dtoRoute.getIdRoute(), errorMessages);
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

        TRoute route = optionalRoute.get();
        routeBusiness.updateRouteFields(dtoRoute, route);  // Delegar la actualización al negocio

        repoRoute.save(route);
        response.success("Ruta actualizada exitosamente.");
        return response;
    }

    // Eliminar ruta
    public ResponseGeneral deleteRouteById(String idRoute) {
        ResponseGeneral response = new ResponseGeneral();
        List<String> errorMessages = new ArrayList<>();

        // Validar existencia de la ruta
        routeBusiness.validateRouteExistence(idRoute, errorMessages);

        if (!errorMessages.isEmpty()) {
            response.error(errorMessages);
            return response;
        }

        repoRoute.deleteById(idRoute);
        response.success("Ruta eliminada exitosamente.");
        return response;
    }

    // Obtener ruta por ID
    public ResponseGeneral getRouteById(String idRoute) {
        ResponseGeneral response = new ResponseGeneral();

        Optional<TRoute> routeOptional = repoRoute.findById(idRoute);
        if (routeOptional.isEmpty()) {
            response.error("La ruta con el ID especificado no existe.");
            return response;
        }

        DtoRoute dtoRoute = genericMapper.map(routeOptional.get(), DtoRoute.class);
        response.success("Ruta encontrada.");
        response.setData(dtoRoute);
        return response;
    }

    // Listar todas las rutas
    public List<DtoRoute> listAllRoutes() {
        return repoRoute.findAll().stream()
                .map(route -> genericMapper.map(route, DtoRoute.class))
                .collect(Collectors.toList());
    }
}
