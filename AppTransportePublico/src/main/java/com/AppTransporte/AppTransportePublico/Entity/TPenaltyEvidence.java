package com.AppTransporte.AppTransportePublico.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TPenaltyEvidence")
public class TPenaltyEvidence {

    @Id
    @Column(nullable = false, length = 36)
    private String idEvidence;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "idPenalty", nullable = false)
    private TPenalty penalty;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String evidenceType;

    @NotNull
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String evidencePath;
}

