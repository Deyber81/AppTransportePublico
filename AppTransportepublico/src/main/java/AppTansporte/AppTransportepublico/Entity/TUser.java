package AppTansporte.AppTransportepublico.Entity;
import java.util.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;



@Data
@Entity
@Table(name = "TUser")
public class TUser {

    @Id
    @Column(nullable = false, length = 36)
    private String idUser;

    @NotNull
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nameUser;

    @NotNull
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String surnameUser;

    @NotNull
    @Email
    @Size(max = 100)
    @Column(nullable = false, unique = true, length = 100)
    private String gmailUser;

    @NotNull
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String passwordUser;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String typeUser;

    @NotNull
    @Column(nullable = false)
    private Date dateCreate;

    @NotNull
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String stateUser;

    @NotNull
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String phoneUser;

    @Column
    private Date lastLogin;

}
