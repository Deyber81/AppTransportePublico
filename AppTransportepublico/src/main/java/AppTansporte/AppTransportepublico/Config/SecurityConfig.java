package AppTansporte.AppTransportepublico.Config;

import AppTansporte.AppTransportepublico.Security.JwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;

    public SecurityConfig(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .cors() // Habilitar CORS
            .and()
            .authorizeHttpRequests()
                .requestMatchers("/auth/login").permitAll() // Permitir login
                .requestMatchers("/User/**").hasAuthority("Admin")
                .requestMatchers("/api/v1/chofer/**").hasAuthority("Chofer") // Acceso exclusivo para Chofer
                .anyRequest().authenticated() // Cualquier otra solicitud requiere autenticación
            .and()
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class); // Filtro JWT

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
