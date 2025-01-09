package AppTansporte.AppTransportepublico.Repository;

import AppTansporte.AppTransportepublico.Entity.TRoute;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoRoute extends JpaRepository<TRoute, String> {

    boolean existsByName(String name);

    boolean existsById(@SuppressWarnings("null") String idRoute);
    boolean existsByNameAndIdRouteNot(String name, String idRoute);

}
