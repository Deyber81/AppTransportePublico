package com.AppTransporte.AppTransportePublico.Repository;

import com.AppTransporte.AppTransportePublico.Entity.TUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull; // Anotación de Spring para indicar no nulo

import java.util.List;
import java.util.Optional;

public interface RepoUser extends JpaRepository<TUser, String> {

    // Encuentra un usuario por su Gmail
    Optional<TUser> findByGmailUser(String gmailUser);
    //Busqueda
    List<TUser> findByNameUserContainingIgnoreCase(String nameUser);
    List<TUser> findBySurnameUserContainingIgnoreCase(String surnameUser);
    List<TUser> findByGmailUserContainingIgnoreCase(String gmailUser);
    List<TUser> findByPhoneUserContaining(String phoneUser);
    // Encuentra un usuario por su número de teléfono
    Optional<TUser> findByPhoneUser(String phoneUser);
    boolean existsByGmailUser(String gmailUser);
    boolean existsByPhoneUser(String phoneUser);
    // Elimina un usuario por su ID
    void deleteById(@NonNull String idUser);
}
