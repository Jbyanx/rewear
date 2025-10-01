package com.devops.backend.rewear.services.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_IN_MINUTES;

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    // Generar un JWT con claims extra
    public String generateToken(UserDetails user, Map<String, Object> extraClaims) {
        Date now = new Date(System.currentTimeMillis());
        Date expiration = new Date(now.getTime() + (EXPIRATION_IN_MINUTES * 60 * 1000));

        return Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .subject(user.getUsername())
                .issuedAt(now)
                .expiration(expiration)
                .claims(extraClaims)
                .signWith(generateKey(), Jwts.SIG.HS256) // Firmar con nuestra secret key
                .compact();
    }

    // Decodificar la secret key
    private SecretKey generateKey() {
        byte[] passwordDecoded = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(passwordDecoded);
    }

    // Extraer username (subject) del JWT
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Extraer todos los claims
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Validar que el token aún no ha expirado
    public boolean isValid(String token, Date date) {
        Date expiration = extractAllClaims(token).getExpiration();
        return expiration.after(date); // válido si expira después de ahora
    }

    // Obtener el token JWT desde el request (ya sin "Bearer ")
    public String extractJwtFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }
        return authorizationHeader.substring(7); // quitar "Bearer "
    }

    // Extraer fecha de expiración
    public Date extractExpiration(String jwt) {
        return extractAllClaims(jwt).getExpiration();
    }
}
