package com.AppTransporte.AppTransportePublico.Bussines.General;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
@Component
public class GenericConverter {

    // Convertir solo los campos necesarios de cualquier entidad a cualquier DTO
    public <T> T convertToDto(Object sourceEntity, Class<T> dtoClass, String... fields) {
        try {
            // Crear una nueva instancia del DTO (sin necesidad de usar un constructor)
            T dtoInstance = dtoClass.getDeclaredConstructor().newInstance();

            for (String field : fields) {
                // Obtener el método getter de la entidad
                String getterMethodName = "get" + capitalize(field);
                Method getterMethod = sourceEntity.getClass().getMethod(getterMethodName);

                // Invocar el getter para obtener el valor
                Object value = getterMethod.invoke(sourceEntity);

                // Obtener el método setter del DTO
                String setterMethodName = "set" + capitalize(field);
                Method setterMethod = dtoClass.getMethod(setterMethodName, value.getClass());

                // Invocar el setter para establecer el valor en el DTO
                setterMethod.invoke(dtoInstance, value);
            }

            return dtoInstance;

        } catch (Exception e) {
            e.printStackTrace();  // Manejo de excepciones
            return null;
        }
    }

    // Método para capitalizar la primera letra del campo
    private String capitalize(String field) {
        return field.substring(0, 1).toUpperCase() + field.substring(1);
    }
}
