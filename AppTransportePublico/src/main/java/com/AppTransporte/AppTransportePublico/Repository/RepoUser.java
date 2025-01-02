package com.AppTransporte.AppTransportePublico.Repository;

import com.AppTransporte.AppTransportePublico.Entity.TUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RepoUser extends JpaRepository<TUser, String> {
     // Buscar usuario por correo
     Optional<TUser> findByGmailUser(String gmailUser);
     // Buscar usuario por teléfono
     Optional<TUser> findByPhoneUser(String phoneUser);
     //Buscar ID del Usuario 
     Optional<TUser> findByIdUser(String idUser);
}
