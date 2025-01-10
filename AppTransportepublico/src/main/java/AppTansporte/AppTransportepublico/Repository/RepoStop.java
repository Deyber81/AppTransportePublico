package AppTansporte.AppTransportepublico.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import AppTansporte.AppTransportepublico.Entity.TStop;

public interface RepoStop extends JpaRepository<TStop, String> {

    // Método para obtener las paradas de una ruta ordenadas por `StopOrder`
    List<TStop> findByIdRouteOrderByStopOrder(String idRoute);
}