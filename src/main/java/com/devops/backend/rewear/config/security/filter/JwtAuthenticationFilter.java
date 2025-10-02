package com.devops.backend.rewear.config.security.filter;

import com.devops.backend.rewear.services.impl.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userService) {
        this.jwtService = jwtService;
        this.userDetailsService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Extraer token del request (sin "Bearer ")
        String accessToken = jwtService.extractJwtFromRequest(request);


        // 2. Si no hay token, continuar con el siguiente filtro
        if (!StringUtils.hasText(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Validar token (fecha de expiración + firma)
        boolean isValid = validateAccessToken(accessToken);
        if (!isValid) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 4. Obtener el username desde el token
            String username = jwtService.extractUsername(accessToken);

            // 5. Cargar al usuario desde la DB
            UserDetails user = userDetailsService.loadUserByUsername(username);

            // 6. Crear Authentication y meterlo en el contexto
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);

            // 7. Continuar con el resto de filtros
            filterChain.doFilter(request, response);

        } catch (io.jsonwebtoken.ExpiredJwtException ex) {
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token expirado",
                    "El token JWT ha expirado. Por favor, inicia sesión nuevamente.");
        } catch (io.jsonwebtoken.JwtException ex) {
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token inválido",
                    "El token JWT es inválido o está malformado.");
        } catch (UsernameNotFoundException ex) {
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Usuario no encontrado",
                    "El usuario asociado al token no existe.");
        } catch (Exception ex) {
            sendErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "Error interno",
                    "Error procesando la autenticación.");
        }
    }

    private boolean validateAccessToken(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }

        Date now = new Date(System.currentTimeMillis());
        return jwtService.isNotExpired(token, now);
    }

    private void sendErrorResponse(HttpServletResponse response,
                                   HttpStatus status,
                                   String error,
                                   String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", error);
        errorResponse.put("status", status.value());
        errorResponse.put("message", message);
        errorResponse.put("timestamp", Instant.now().toString());

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
