package com.devops.backend.rewear.util;

import java.util.regex.Pattern;

public class PasswordValidator {

    // Patrones para validación
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile(".*[A-Z].*");
    private static final Pattern LOWERCASE_PATTERN = Pattern.compile(".*[a-z].*");
    private static final Pattern DIGIT_PATTERN = Pattern.compile(".*\\d.*");
    private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");

    // Mínima longitud
    private static final int MIN_LENGTH = 8;

    /**
     * Valida que la contraseña cumpla con todos los criterios de seguridad:
     * - Mínimo 8 caracteres
     * - Al menos una mayúscula
     * - Al menos una minúscula
     * - Al menos un número
     * - Al menos un carácter especial
     */
    public static boolean isValid(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }

        // Verificar longitud mínima
        if (password.length() < MIN_LENGTH) {
            return false;
        }

        // Verificar al menos una mayúscula
        if (!UPPERCASE_PATTERN.matcher(password).matches()) {
            return false;
        }

        // Verificar al menos una minúscula
        if (!LOWERCASE_PATTERN.matcher(password).matches()) {
            return false;
        }

        // Verificar al menos un número
        if (!DIGIT_PATTERN.matcher(password).matches()) {
            return false;
        }

        // Verificar al menos un carácter especial
        if (!SPECIAL_CHAR_PATTERN.matcher(password).matches()) {
            return false;
        }

        return true;
    }


    /**
     * Método que devuelve qué criterios no cumple la contraseña
     * Útil para mostrar mensajes específicos al usuario
     */
    public static String getValidationMessage(String password) {
        if (password == null || password.isEmpty()) {
            return "La contraseña no puede estar vacía";
        }

        StringBuilder message = new StringBuilder();

        if (password.length() < MIN_LENGTH) {
            message.append("- Debe tener al menos 8 caracteres\n");
        }

        if (!UPPERCASE_PATTERN.matcher(password).matches()) {
            message.append("- Debe contener al menos una letra mayúscula\n");
        }

        if (!LOWERCASE_PATTERN.matcher(password).matches()) {
            message.append("- Debe contener al menos una letra minúscula\n");
        }

        if (!DIGIT_PATTERN.matcher(password).matches()) {
            message.append("- Debe contener al menos un número\n");
        }

        if (!SPECIAL_CHAR_PATTERN.matcher(password).matches()) {
            message.append("- Debe contener al menos un carácter especial (!@#$%^&*()_+-=[]{}|;':\"\\,.<>/?)\n");
        }

        return message.length() > 0 ?
                "La contraseña no cumple con los siguientes criterios:\n" + message.toString() :
                "La contraseña es válida";
    }

}