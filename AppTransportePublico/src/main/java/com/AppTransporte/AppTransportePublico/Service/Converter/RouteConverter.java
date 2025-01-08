package com.AppTransporte.AppTransportePublico.Service.Converter;

import com.AppTransporte.AppTransportePublico.Dto.Requets.RequestRoute;
import com.AppTransporte.AppTransportePublico.Dto.Response.DtoRoute;
import com.AppTransporte.AppTransportePublico.Entity.TRoute;
import com.AppTransporte.AppTransportePublico.Service.GenericService.GenericConverter;
import org.springframework.stereotype.Component;

@Component
public class RouteConverter extends GenericConverter<RequestRoute, DtoRoute, TRoute> {

    public RouteConverter() {
        super(
            // RequestRoute → DtoRoute
            request -> {
                DtoRoute dto = new DtoRoute();
                dto.setName(request.getName());
                dto.setDescription(request.getDescription());
                dto.setStartLatitude(request.getStartLatitude());
                dto.setStartLongitude(request.getStartLongitude());
                dto.setEndLatitude(request.getEndLatitude());
                dto.setEndLongitude(request.getEndLongitude());
                return dto;
            },
            // DtoRoute → TRoute
            dto -> {
                TRoute entity = new TRoute();
                entity.setIdRoute(dto.getIdRoute());
                entity.setName(dto.getName());
                entity.setDescription(dto.getDescription());
                entity.setStartLatitude(dto.getStartLatitude());
                entity.setStartLongitude(dto.getStartLongitude());
                entity.setEndLatitude(dto.getEndLatitude());
                entity.setEndLongitude(dto.getEndLongitude());
                return entity;
            },
            // TRoute → DtoRoute
            entity -> {
                DtoRoute dto = new DtoRoute();
                dto.setIdRoute(entity.getIdRoute());
                dto.setName(entity.getName());
                dto.setDescription(entity.getDescription());
                dto.setStartLatitude(entity.getStartLatitude());
                dto.setStartLongitude(entity.getStartLongitude());
                dto.setEndLatitude(entity.getEndLatitude());
                dto.setEndLongitude(entity.getEndLongitude());
                return dto;
            }
        );
    }
}
