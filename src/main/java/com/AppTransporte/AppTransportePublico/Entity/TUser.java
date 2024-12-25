package com.AppTransporte.AppTransportePublico.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TUser")
@Getter
@Setter
public class TUser implements Serializable {

    @Id
    @Column(name = "IdUser", nullable = false) 
    private String idUser; 

    @Column(name = "NameUser", nullable = false)
    private String nameUser;

    @Column(name = "SurnameUser", nullable = false)
    private String surnameUser;

    @Column(name = "GmailUser", nullable = false, unique = true)
    private String gmailUser;

    @Column(name = "PasswordUser", nullable = false)
    private String passwordUser;

    @Column(name = "TypeUser", nullable = false)
    private String typeUser;

    @Column(name = "DateCreate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)  // Especificando tipo de dato temporal para Date
    private Date dateCreate;

    @Column(name = "StateUser", nullable = false)
    private String stateUser = "Inactivo";
    
    @Column(name = "PhoneUser", nullable = false)
    private String phoneUser;

    @Column(name = "LastLogin")
    @Temporal(TemporalType.TIMESTAMP)  // Especificando tipo de dato temporal para Date
    private Date lastLogin;

       // Relación Uno a Muchos con TVehicle
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TVehicle> vehicles;
}
