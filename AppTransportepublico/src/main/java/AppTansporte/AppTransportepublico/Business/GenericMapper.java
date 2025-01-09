package AppTansporte.AppTransportepublico.Business;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GenericMapper {

    private final ModelMapper modelMapper;

    public GenericMapper() {
        this.modelMapper = new ModelMapper();
    }

    public <S, T> T map(S source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }

    public <S, T> void map(S source, T target) {
        modelMapper.map(source, target);
    }
}
