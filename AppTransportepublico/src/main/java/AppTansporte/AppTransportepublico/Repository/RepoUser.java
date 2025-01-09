package AppTansporte.AppTransportepublico.Repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import AppTansporte.AppTransportepublico.Entity.TUser;

public interface RepoUser extends JpaRepository<TUser, String> {
     // Buscar usuario por correo
     Optional<TUser> findByGmailUser(String gmailUser);
     // Buscar usuario por teléfono
     Optional<TUser> findByPhoneUser(String phoneUser);
     //Buscar ID del Usuario 
     Optional<TUser> findByIdUser(String idUser);
}
