package AppTansporte.AppTransportepublico;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Deshabilita CSRF para facilitar pruebas
            .authorizeHttpRequests()
                .anyRequest().permitAll(); // Permitir acceso a todos los endpoints
        return http.build();
    }
}