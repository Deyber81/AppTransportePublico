package AppTansporte.AppTransportepublico.Security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    // Generar el token JWT usando el correo electrónico
    @SuppressWarnings("deprecation")
    public String generateToken(String email, String typeUser, String name, String surname) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
    
        // Agregar reclamos adicionales al token
        return Jwts.builder()
                .setSubject(email) // Usar el correo electrónico como sujeto del token
                .claim("typeUser", typeUser)
                .claim("name", name) // Reclamo del nombre
                .claim("surname", surname) // Reclamo del apellido
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    

    // Obtener el correo electrónico del token JWT
    @SuppressWarnings("deprecation")
    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public String getClaimFromToken(String token, String claimKey) {
        @SuppressWarnings("deprecation")
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.get(claimKey, String.class);
    }

    // Validar el token JWT
    @SuppressWarnings("deprecation")
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token expirado: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("Token no soportado: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Token malformado: " + e.getMessage());
        } catch (SignatureException e) {
            System.out.println("Firma no válida: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Token vacío: " + e.getMessage());
        }
        return false;
    }
}
