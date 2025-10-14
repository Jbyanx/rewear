package com.devops.backend.rewear.exceptions;

import com.devops.backend.rewear.dtos.response.ApiErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 🔹 Usuario o email duplicado
    @ExceptionHandler({UsernameAlreadyExistsException.class, EmailAlreadyExistsException.class})
    public ResponseEntity<ApiErrorResponse> handleDuplicateUserData(
            Exception ex, HttpServletRequest request
    ) {
        return buildResponse(ex, HttpStatus.BAD_REQUEST, request);
    }

    // 🔹 Usuario o prenda no encontrada
    @ExceptionHandler({UserNotFoundException.class, WearNotFoundException.class, EntityNotFoundException.class})
    public ResponseEntity<ApiErrorResponse> handleNotFound(
            Exception ex, HttpServletRequest request
    ) {
        return buildResponse(ex, HttpStatus.NOT_FOUND, request);
    }

    // 🔹 Permiso denegado
    @ExceptionHandler({PermissionDeniedException.class, AccessDeniedException.class})
    public ResponseEntity<ApiErrorResponse> handleForbidden(
            Exception ex, HttpServletRequest request
    ) {
        return buildResponse(ex, HttpStatus.FORBIDDEN, request);
    }

    // 🔹 No autenticado
    @ExceptionHandler({UserNotAuthenticatedException.class, BadCredentialsException.class})
    public ResponseEntity<ApiErrorResponse> handleUnauthorized(
            Exception ex, HttpServletRequest request
    ) {
        return buildResponse(ex, HttpStatus.UNAUTHORIZED, request);
    }

    // 🔹 Validaciones por @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request
    ) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return buildResponse(new RuntimeException(message), HttpStatus.BAD_REQUEST, request);
    }

    // 🔹 Violaciones de constraint (@Min, @NotNull, etc.)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex, HttpServletRequest request
    ) {
        String message = ex.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining("; "));

        return buildResponse(new RuntimeException(message), HttpStatus.BAD_REQUEST, request);
    }

    // 🔹 Tipo de argumento incorrecto
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request
    ) {
        String message = "Tipo de argumento inválido para el parámetro '" + ex.getName() + "'";
        return buildResponse(new RuntimeException(message), HttpStatus.BAD_REQUEST, request);
    }

    // 🔹 Falta un parámetro obligatorio
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingParam(
            MissingServletRequestParameterException ex, HttpServletRequest request
    ) {
        String message = "Falta el parámetro requerido: " + ex.getParameterName();
        return buildResponse(new RuntimeException(message), HttpStatus.BAD_REQUEST, request);
    }

    // 🔹 Cuerpo JSON malformado o vacío
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleUnreadableBody(
            HttpMessageNotReadableException ex, HttpServletRequest request
    ) {
        return buildResponse(new RuntimeException("Cuerpo de la solicitud inválido o malformado."),
                HttpStatus.BAD_REQUEST, request);
    }
    // 🔹 credenciales erroneas
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleBadCredentials(
            HttpMessageNotReadableException ex, HttpServletRequest request
    ) {
        return buildResponse(new RuntimeException("Credenciales erroneas, verifique username o password."),
                HttpStatus.BAD_REQUEST, request);
    }

    // 🔹 Violación de integridad (FK, unique, etc.)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataIntegrity(
            DataIntegrityViolationException ex, HttpServletRequest request
    ) {
        return buildResponse(new RuntimeException("Violación de integridad de datos (FK o valor duplicado)."),
                HttpStatus.CONFLICT, request);
    }

    // 🔹 Método HTTP no permitido
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodNotAllowed(
            HttpRequestMethodNotSupportedException ex, HttpServletRequest request
    ) {
        return buildResponse(ex, HttpStatus.METHOD_NOT_ALLOWED, request);
    }

    // 🔹 Ruta inexistente
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFoundRoute(
            NoHandlerFoundException ex, HttpServletRequest request
    ) {
        return buildResponse(new RuntimeException("Ruta no encontrada."), HttpStatus.NOT_FOUND, request);
    }

    // ==========================
    // 🔸 NUEVAS EXCEPCIONES
    // ==========================

    // 🔹 Intercambio no encontrado
    @ExceptionHandler(ExchangeNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleExchangeNotFound(
            ExchangeNotFoundException ex, HttpServletRequest request
    ) {
        return buildResponse(ex, HttpStatus.NOT_FOUND, request);
    }

    // 🔹 Intercambio inválido (por estado o usuario incorrecto)
    @ExceptionHandler(InvalidExchangeException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidExchange(
            InvalidExchangeException ex, HttpServletRequest request
    ) {
        return buildResponse(ex, HttpStatus.BAD_REQUEST, request);
    }

    // 🔹 Contraseña incorrecta
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidPassword(
            InvalidPasswordException ex, HttpServletRequest request
    ) {
        return buildResponse(ex, HttpStatus.UNAUTHORIZED, request);
    }

    // 🔹 Token inválido o expirado
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidToken(
            InvalidTokenException ex, HttpServletRequest request
    ) {
        return buildResponse(ex, HttpStatus.UNAUTHORIZED, request);
    }

    // 🔹 Recurso no encontrado
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNoResourceFound(
            NoResourceFoundException ex, HttpServletRequest request
    ) {
        return buildResponse(ex, HttpStatus.UNAUTHORIZED, request);
    }
    // 🔹 Review no completada
    @ExceptionHandler(InvalidReviewException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidReview(
            InvalidReviewException ex, HttpServletRequest request
    ) {
        return buildResponse(ex, HttpStatus.UNAUTHORIZED, request);
    }

    // 🔹 Error genérico (última barrera)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(
            Exception ex, HttpServletRequest request
    ) {
        log.error("❌ Error interno en {}: {}", request.getRequestURI(), ex.getMessage(), ex);

        return buildResponse(
                new RuntimeException("Error interno en el servidor."),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );
    }

    // 🔹 Token JWT expirado o inválido (biblioteca io.jsonwebtoken)
    @ExceptionHandler(io.jsonwebtoken.JwtException.class)
    public ResponseEntity<ApiErrorResponse> handleJwtExceptions(
            io.jsonwebtoken.JwtException ex, HttpServletRequest request
    ) {
        String message;

        if (ex instanceof io.jsonwebtoken.ExpiredJwtException) {
            message = "El token JWT ha expirado. Por favor, inicie sesión nuevamente.";
        } else if (ex instanceof io.jsonwebtoken.MalformedJwtException) {
            message = "El token JWT tiene un formato inválido.";
        } else if (ex instanceof io.jsonwebtoken.SignatureException) {
            message = "Firma del token JWT inválida.";
        } else {
            message = "Token JWT inválido o no reconocido.";
        }

        return buildResponse(new RuntimeException(message), HttpStatus.UNAUTHORIZED, request);
    }

    // 🧩 Método auxiliar
    private ResponseEntity<ApiErrorResponse> buildResponse(Exception ex, HttpStatus status, HttpServletRequest request) {
        ApiErrorResponse response = ApiErrorResponse.of(
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                status.value(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(response);
    }
}
