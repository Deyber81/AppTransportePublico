package com.AppTransporte.AppTransportePublico.Repository;
import com.AppTransporte.AppTransportePublico.Entity.TVehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepoVehicle extends JpaRepository<TVehicle, String> {
    Optional<TVehicle> findByLicensePlate(String licensePlate);
}
