package com.AppTransporte.AppTransportePublico.Service;

import com.AppTransporte.AppTransportePublico.Dto.Response.DtoRoute;
import com.AppTransporte.AppTransportePublico.Dto.ResponseGeneral;
import com.AppTransporte.AppTransportePublico.Dto.Requets.RequestRoute;
import com.AppTransporte.AppTransportePublico.Entity.TRoute;
import com.AppTransporte.AppTransportePublico.Repository.RepoRoute;
import com.AppTransporte.AppTransportePublico.Service.GenericService.GenericConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RouteService {

    @Autowired
    private RepoRoute repoRoute;

    @Autowired
    private GenericConverter<RequestRoute, DtoRoute, TRoute> genericConverter;

    public ResponseGeneral insertRoute(RequestRoute requestRoute) {
        ResponseGeneral response = new ResponseGeneral();
        try {
            DtoRoute dtoRoute = genericConverter.toDTO(requestRoute);
            TRoute route = genericConverter.toEntity(dtoRoute);

            route.setIdRoute(UUID.randomUUID().toString());
            repoRoute.save(route);

            response.success("Ruta registrada exitosamente.");
            return response;
        } catch (Exception e) {
            response.error("Error al registrar la ruta.");
            response.getErrors().add(e.getMessage());
            return response;
        }
    }

    public ResponseGeneral listAllRoutes() {
        ResponseGeneral response = new ResponseGeneral();
        List<TRoute> routes = repoRoute.findAll();

        if (routes.isEmpty()) {
            response.setMessage("La lista de rutas está vacía.");
            response.setData(null);
            response.setStatusCode(HttpStatus.OK);
            return response;
        }

        List<DtoRoute> routeDtos = routes.stream()
                .map(genericConverter::toDTOFromEntity)
                .collect(Collectors.toList());

        response.setMessage("Lista de rutas obtenida exitosamente.");
        response.setData(routeDtos);
        response.setStatusCode(HttpStatus.OK);
        return response;
    }

    public ResponseGeneral getRouteById(String idRoute) {
        ResponseGeneral response = new ResponseGeneral();
        Optional<TRoute> route = repoRoute.findById(idRoute);

        if (route.isEmpty()) {
            response.error("La ruta no existe.");
            return response;
        }

        DtoRoute dtoRoute = genericConverter.toDTOFromEntity(route.get());
        response.setData(dtoRoute);
        response.success("Ruta encontrada exitosamente.");
        return response;
    }

    public ResponseGeneral updateRoute(RequestRoute requestRoute, String idRoute) {
        ResponseGeneral response = new ResponseGeneral();
        Optional<TRoute> existingRoute = repoRoute.findById(idRoute);

        if (existingRoute.isEmpty()) {
            response.error("La ruta no existe.");
            return response;
        }

        TRoute route = existingRoute.get();
        route.setName(requestRoute.getName());
        route.setDescription(requestRoute.getDescription());
        route.setStartLatitude(requestRoute.getStartLatitude());
        route.setStartLongitude(requestRoute.getStartLongitude());
        route.setEndLatitude(requestRoute.getEndLatitude());
        route.setEndLongitude(requestRoute.getEndLongitude());

        repoRoute.save(route);
        response.success("Ruta actualizada exitosamente.");
        return response;
    }

    public ResponseGeneral deleteRouteById(String idRoute) {
        ResponseGeneral response = new ResponseGeneral();
        Optional<TRoute> route = repoRoute.findById(idRoute);

        if (route.isEmpty()) {
            response.error("La ruta no existe.");
            return response;
        }

        repoRoute.delete(route.get());
        response.success("Ruta eliminada exitosamente.");
        return response;
    }
}

