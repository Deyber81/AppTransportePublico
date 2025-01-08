package com.AppTransporte.AppTransportePublico.Bussines;

import com.AppTransporte.AppTransportePublico.Dto.Response.DtoRoute;
import com.AppTransporte.AppTransportePublico.Repository.RepoRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RouteBusiness {

    @Autowired
    private RepoRoute repoRoute;

    /**
     * Valida si el nombre de la ruta ya existe en la base de datos.
     * 
     * @param dtoRoute      DTO de la ruta que se está validando.
     * @param errorMessages Lista de mensajes de error para añadir mensajes de validación.
     */
    public void validateRouteNameUniqueness(DtoRoute dtoRoute, List<String> errorMessages) {
        // Verifica si ya existe una ruta con el mismo nombre
        boolean nameExists = repoRoute.existsByName(dtoRoute.getName());

        if (nameExists) {
            errorMessages.add("Ya existe una ruta con el nombre '" + dtoRoute.getName() + "'.");
        }
    }
}
