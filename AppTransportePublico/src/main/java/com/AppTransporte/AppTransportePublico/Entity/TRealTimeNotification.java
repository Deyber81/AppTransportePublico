package com.AppTransporte.AppTransportePublico.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "TRealTimeNotification")
public class TRealTimeNotification {

    @Id
    @Column(nullable = false, length = 36)
    private String idNotification;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "idVehicle", nullable = false)
    private TVehicle vehicle;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String notificationType;

    @NotNull
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String message;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime timestamp;

    @NotNull
    @Column(nullable = false, columnDefinition = "BIT DEFAULT 0")
    private Boolean isRead;
}
