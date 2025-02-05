package AppTansporte.AppTransportepublico.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AppTansporte.AppTransportepublico.Business.GenericMapper;
import AppTansporte.AppTransportepublico.Dto.Response.DtoRealTimeLocation;
import AppTansporte.AppTransportepublico.Entity.TRealTimeLocation;
import AppTansporte.AppTransportepublico.Repository.RealTimeLocationRepository;

@Service
public class RealTimeLocationService {
   @Autowired
    private RealTimeLocationRepository realTimeLocationRepository; // Repositorio para manejar la base de datos
    @Autowired
    private GenericMapper genericMapper; // Inyectamos el GenericMapper

    public void updateRealTimeLocation(DtoRealTimeLocation dto) {
        // Mapeamos el DTO a la entidad
        TRealTimeLocation realTimeLocation = genericMapper.map(dto, TRealTimeLocation.class);

        // Guardamos la entidad en la base de datos
        realTimeLocationRepository.save(realTimeLocation);
    }
}

