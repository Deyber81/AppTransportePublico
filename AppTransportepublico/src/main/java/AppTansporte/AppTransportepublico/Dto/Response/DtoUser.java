package AppTansporte.AppTransportepublico.Dto.Response;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

@Data
public class DtoUser {

    private String idUser;

    private String nameUser;

    private String surnameUser;

    private String gmailUser;
    
    private String passwordUser;

    private String typeUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Lima")
    private Date dateCreate;

    private String stateUser;

    private String phoneUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Lima")
    private Date lastLogin;
}
