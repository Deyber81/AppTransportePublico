package AppTansporte.AppTransportepublico.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import AppTansporte.AppTransportepublico.Entity.TVehicle;

public interface RepoVehicle extends JpaRepository<TVehicle, String> {
    boolean existsByLicensePlate(String licensePlate);
    boolean existsByIdUser(String idUser);
}

