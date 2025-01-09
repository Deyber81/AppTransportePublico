package AppTansporte.AppTransportepublico.Dto.Response;

import lombok.Data;

@Data
public class DtoVehicle {

    private String idVehicle;

    private String idUser;

    private String licensePlate;

    private String model;

    private int capacity;

    private String GPSDeviceId;
    
}
