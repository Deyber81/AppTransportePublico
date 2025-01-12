package AppTansporte.AppTransportepublico.Business;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.stereotype.Component;

@Component
public class GenericMapper {

    private final ModelMapper modelMapper;

    public GenericMapper() {
        this.modelMapper = new ModelMapper();
        configureGlobalMappings();
    }

    public <S, T> T map(S source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }

    public <S, T> void map(S source, T target) {
        modelMapper.map(source, target);
    }
    private void configureGlobalMappings() {
        // Configuración global del ModelMapper
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true) // Habilita coincidencia por nombres de campos
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE) // Permite acceso a campos privados
                .setAmbiguityIgnored(true) // Ignora conflictos de ambigüedad
                .setSkipNullEnabled(true); // Ignora valores nulos
    
        // Mapeo genérico para excluir conflictos
        modelMapper.getConfiguration().setPropertyCondition(context -> {
            context.getMapping().getLastDestinationProperty().getName();
            return true;
        });
    }
}
