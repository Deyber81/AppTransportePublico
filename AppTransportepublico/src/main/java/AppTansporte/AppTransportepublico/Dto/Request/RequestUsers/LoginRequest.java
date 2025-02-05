package AppTansporte.AppTransportepublico.Dto.Request.RequestUsers;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "El formato del correo electrónico no es válido.")
    private String gmailUser;

    @NotBlank(message = "La contraseña es obligatoria.")
    private String passwordUser;
}
