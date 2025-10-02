package com.devops.backend.rewear.services.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${SECURITY_JWT_ACCESS_EXPIRATION_IN_MINUTES}")
    private Long ACCESS_EXPIRATION_IN_MINUTES;

    @Value("${SECURITY_JWT_REFRESH_EXPIRATION_IN_MINUTES}")
    private Long REFRESH_EXPIRATION_IN_MINUTES;

    @Value("${SECURITY_JWT_SECRET_KEY}")
    private String SECRET_KEY;

    // Generar Access Token
    public String generateAccessToken(UserDetails user, Map<String, Object> extraClaims) {
        return buildToken(user, extraClaims, ACCESS_EXPIRATION_IN_MINUTES);
    }

    // Generar Refresh Token
    public String generateRefreshToken(UserDetails user, Map<String, Object> extraClaims) {
        return buildToken(user, extraClaims, REFRESH_EXPIRATION_IN_MINUTES);
    }

    private String buildToken(UserDetails user, Map<String, Object> extraClaims, Long expirationInMinutes) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationInMinutes * 60 * 1000);

        return Jwts.builder()
                .header().type("JWT").and()
                .subject(user.getUsername())
                .issuedAt(now)
                .expiration(expiration)
                .claims(extraClaims)
                .signWith(generateKey(), Jwts.SIG.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public boolean isNotExpired(String token, Date now) {
        return extractExpiration(token).after(now);
    }

    private SecretKey generateKey() {
        byte[] passwordDecoded = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(passwordDecoded);
    }

    public String extractJwtFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (!org.springframework.util.StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }
        return authorizationHeader.substring(7);
    }
}
