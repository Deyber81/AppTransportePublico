package com.AppTransporte.AppTransportePublico.Bussines.User.UserResponse;

import java.util.ArrayList;
import java.util.List;
import com.AppTransporte.AppTransportePublico.Dto.DtoUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseGetAllUsers extends com.AppTransporte.AppTransportePublico.Bussines.ResponseGeneral {

    // La clase Dto que contiene ListUsers
    @Getter
    @Setter
    public static class Dto {
        private List<DtoUser> listUsers = new ArrayList<>();
    }

    private Dto dto;

    public ResponseGetAllUsers() {
        this.dto = new Dto();
    }
}

