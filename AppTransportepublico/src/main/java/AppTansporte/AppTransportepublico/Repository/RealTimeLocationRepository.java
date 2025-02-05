package AppTansporte.AppTransportepublico.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import AppTansporte.AppTransportepublico.Entity.TRealTimeLocation;

public interface RealTimeLocationRepository extends JpaRepository<TRealTimeLocation, String> {
    // Aquí puedes agregar consultas personalizadas si las necesitas
}
