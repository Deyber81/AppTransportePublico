package AppTansporte.AppTransportepublico.Dto.Response;


import lombok.Data;
@Data
public class DtoStop {

    private String idStop;

    private String idRoute;

    private String stopName;

    private Double latitude;

    private Double longitude;

    private int stopOrder;

    private int stopDuration;
}
