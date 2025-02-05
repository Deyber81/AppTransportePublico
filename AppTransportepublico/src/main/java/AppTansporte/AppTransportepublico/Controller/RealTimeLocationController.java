package AppTansporte.AppTransportepublico.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import AppTansporte.AppTransportepublico.Business.GenericMapper;
import AppTansporte.AppTransportepublico.Dto.Request.RequestRealTimeLocation.CreateRealTimeLocationRequest;
import AppTansporte.AppTransportepublico.Dto.Response.DtoRealTimeLocation;
import AppTansporte.AppTransportepublico.Services.RealTimeLocationService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/real-time-location")
public class RealTimeLocationController {
   @Autowired
    private  GenericMapper genericMapper; // Inyectamos el GenericMapper
    @Autowired
    private RealTimeLocationService realTimeLocationService; // Servicio encargado de la lógica

    // Endpoint para recibir la ubicación en tiempo real
    @PostMapping("/update")
    public ResponseEntity<String> updateLocation(@RequestBody @Valid CreateRealTimeLocationRequest request) {
        // Mapeamos la solicitud (request) a un DTO
        DtoRealTimeLocation dto = genericMapper.map(request, DtoRealTimeLocation.class);
        
        // Llamamos al servicio pasando el DTO
        realTimeLocationService.updateRealTimeLocation(dto);
        
        return ResponseEntity.ok("Ubicación en tiempo real actualizada correctamente");
    }
}
