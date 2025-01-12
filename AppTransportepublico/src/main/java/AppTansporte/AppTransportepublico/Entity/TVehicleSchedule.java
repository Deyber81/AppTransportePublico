    package AppTansporte.AppTransportepublico.Entity;

    import jakarta.persistence.*;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import lombok.AllArgsConstructor;
    import java.time.LocalTime;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    @Table(name = "TVehicleSchedule")
    public class TVehicleSchedule {

        @Id
        @Column(name = "IdSchedule", nullable = false, length = 36)
        private String idSchedule;

        @Column(name = "IdVehicle", nullable = false, length = 36)
        private String idVehicle;
    
        @Column(name = "IdRoute", nullable = false, length = 36)
        private String idRoute;

        @Column(name = "DayOfWeek", nullable = false, length = 20)
        private String dayOfWeek;

        @Column(name = "DepartureTime", nullable = false)
        private LocalTime departureTime;

        @Column(name = "ArrivalTime", nullable = false)
        private LocalTime arrivalTime;

        @Column(name = "Direction", nullable = false)
        private String direction;
    }
