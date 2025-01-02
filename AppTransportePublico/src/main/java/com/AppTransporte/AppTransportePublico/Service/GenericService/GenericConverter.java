package com.AppTransporte.AppTransportePublico.Service.GenericService;

import java.util.function.Function;

public class GenericConverter<REQ, DTO, ENT> {

    private final Function<REQ, DTO> toDTOFunction;
    private final Function<DTO, ENT> toEntityFunction;
    private final Function<ENT, DTO> toDtoFromEntityFunction;

    public GenericConverter(Function<REQ, DTO> toDTOFunction,
                            Function<DTO, ENT> toEntityFunction,
                            Function<ENT, DTO> toDtoFromEntityFunction) {
        this.toDTOFunction = toDTOFunction;
        this.toEntityFunction = toEntityFunction;
        this.toDtoFromEntityFunction = toDtoFromEntityFunction;
    }

    // Convierte Request a DTO
    public DTO toDTO(REQ request) {
        return toDTOFunction.apply(request);
    }

    // Convierte DTO a Entidad
    public ENT toEntity(DTO dto) {
        return toEntityFunction.apply(dto);
    }

    // Convierte Entidad a DTO
    public DTO toDTOFromEntity(ENT entity) {
        return toDtoFromEntityFunction.apply(entity);
    }
}
