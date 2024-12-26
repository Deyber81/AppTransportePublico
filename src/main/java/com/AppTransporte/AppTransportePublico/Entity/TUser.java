package com.AppTransporte.AppTransportePublico.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;

    @Column(name = "StateUser", nullable = false)
    private String stateUser;

    @Column(name = "PhoneUser", nullable = false)
    private String phoneUser;

    @Column(name = "LastLogin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TVehicle> vehicles;

}
