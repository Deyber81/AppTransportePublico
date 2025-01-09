package AppTansporte.AppTransportepublico.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TVehicle")
public class TVehicle {

    @Id
    private String idVehicle;

    @Column(name = "IdUser", nullable = false)
    private String idUser;

    @Column(name = "LicensePlate", nullable = false, unique = true)
    private String licensePlate;

    @Column(name = "Model", nullable = false)
    private String model;

    @Column(name = "Capacity", nullable = false)
    private int capacity;

    @Column(name = "GPSDeviceId", nullable = false,unique = true)
    private String GPSDeviceId;

    @ManyToOne
    @JoinColumn(name = "IdUser", insertable = false, updatable = false) // Relación con TUser
    private TUser user;
}