package AppTansporte.AppTransportepublico.Dto.Request.RequestUsers;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateUserRequest {

    @NotNull(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nameUser;

    @NotNull(message = "El apellido es obligatorio")
    @Size(min = 3, max = 150, message = "El apellido debe tener entre 3 y 150 caracteres")
    private String surnameUser;

    @NotNull(message = "El correo es obligatorio")
    @Email(message = "Debe proporcionar un correo válido")
    private String gmailUser;

    @NotNull(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String passwordUser;

    @NotNull(message = "El tipo de usuario es obligatorio")
    private String typeUser;

    @NotNull(message = "El número telefonico es obligatorio")
    @Pattern(regexp = "\\d{9}", message = "El teléfono debe contener exactamente 9 dígitos")
    private String phoneUser;
}