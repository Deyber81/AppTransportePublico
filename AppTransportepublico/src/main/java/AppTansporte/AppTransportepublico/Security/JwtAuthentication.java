package AppTansporte.AppTransportepublico.Security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthentication extends AbstractAuthenticationToken {

    private final String principal; // El nombre del usuario autenticado (email, username, etc.)
    private String credentials; // En este caso, puede ser el token (opcional)

    // Constructor para autenticación sin credenciales
    public JwtAuthentication(String principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = null;
        this.setAuthenticated(true);
    }

    // Constructor con credenciales (opcional)
    public JwtAuthentication(String principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
