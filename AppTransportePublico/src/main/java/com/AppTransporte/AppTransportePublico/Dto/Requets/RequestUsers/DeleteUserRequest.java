package com.AppTransporte.AppTransportePublico.Dto.Requets.RequestUsers;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DeleteUserRequest {

    @NotNull(message = "El ID del usuario es obligatorio")
    private String idUser;
}