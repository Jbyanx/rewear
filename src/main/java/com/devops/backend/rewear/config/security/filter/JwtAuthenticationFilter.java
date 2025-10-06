package com.devops.backend.rewear.config.security.filter;

import com.devops.backend.rewear.services.impl.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
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
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1) Extraer token (puede devolver null o cadena vacía)
        String accessToken = jwtService.extractJwtFromRequest(request);

        // 2) Si no hay token, continuar normalmente
        if (!StringUtils.hasText(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 3) Intentamos procesar el token (cualquier excepción JWT se atrapará aquí)
            // Extraemos el username (puede lanzar ExpiredJwtException u otras JwtException)
            String username = jwtService.extractUsername(accessToken);

            // Cargamos el usuario desde la DB
            UserDetails user = userDetailsService.loadUserByUsername(username);

            // Si no hay autenticación previa, seteamos el contexto
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

            // 4) Continuar con la cadena de filtros
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException ex) {
            // Token expirado -> 401
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token expirado",
                    "El token JWT ha expirado. Por favor, inicia sesión nuevamente.");
            return;
        } catch (JwtException ex) {
            // Otros errores de JWT (firma inválida, formato, etc.) -> 401
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token inválido",
                    "El token JWT es inválido o está malformado.");
            return;
        } catch (UsernameNotFoundException ex) {
            // Usuario no existe -> 401
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Usuario no encontrado",
                    "El usuario asociado al token no existe.");
            return;
        } catch (Exception ex) {
            // Error inesperado -> 500
            sendErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "Error interno",
                    "Error procesando la autenticación.");
        }
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
