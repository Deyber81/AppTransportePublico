package com.AppTransporte.AppTransportePublico.Service.Converter;
import org.springframework.stereotype.Component;
import com.AppTransporte.AppTransportePublico.Dto.Response.DtoVehicle;
import com.AppTransporte.AppTransportePublico.Dto.Requets.RequestVehicle;
import com.AppTransporte.AppTransportePublico.Entity.TVehicle;
import com.AppTransporte.AppTransportePublico.Service.GenericService.GenericConverter;

@Component
public class VehicleConverter extends GenericConverter<RequestVehicle, DtoVehicle, TVehicle> {

    public VehicleConverter() {
        super(
            // Request → DTO
            request -> {
                DtoVehicle dto = new DtoVehicle();
                dto.setIdUser(request.getIdUser());
                dto.setLicensePlate(request.getLicensePlate());
                dto.setModel(request.getModel());
                dto.setCapacity(request.getCapacity());
                return dto;
            },
            // DTO → Entidad
            dto -> {
                TVehicle entity = new TVehicle();
                entity.setLicensePlate(dto.getLicensePlate());
                entity.setModel(dto.getModel());
                entity.setCapacity(dto.getCapacity());
                return entity;
            },
            // Entidad → DTO
            entity -> {
                DtoVehicle dto = new DtoVehicle();
                dto.setIdVehicle(entity.getIdVehicle());
                dto.setIdUser(entity.getUser().getIdUser());
                dto.setLicensePlate(entity.getLicensePlate());
                dto.setModel(entity.getModel());
                dto.setCapacity(entity.getCapacity());
                return dto;
            }
        );
    }
}
