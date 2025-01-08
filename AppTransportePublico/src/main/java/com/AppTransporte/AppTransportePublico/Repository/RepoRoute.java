package com.AppTransporte.AppTransportePublico.Repository;
import com.AppTransporte.AppTransportePublico.Entity.TRoute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoRoute extends JpaRepository<TRoute, String> {
}
