package AppTansporte.AppTransportepublico.Repository;

import AppTansporte.AppTransportepublico.Entity.TRoute;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoRoute extends JpaRepository<TRoute, String> {

    List<TRoute> findByName(String name);

}

