package www.edu.udb.sv.util;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import www.edu.udb.sv.bean.Usuario;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsuarioValidator implements Validator {

    // Soporte para la clase Usuario
    @Override
    public boolean supports(Class<?> clazz) {
        return Usuario.class.equals(clazz);
    }

    // Método de validación
    @Override
    public void validate(Object target, Errors errors) {
        Usuario usuario = (Usuario) target;

        // Validar campos vacíos
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "field.username.required", "El nombre de usuario es obligatorio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.password.required", "La contraseña es obligatoria");

        // Validación del nombre de usuario (mínimo 5 caracteres)
        if (usuario.getUsername() != null && usuario.getUsername().length() < 5) {
            errors.rejectValue("username", "field.username.tooShort", "El nombre de usuario debe tener al menos 5 caracteres");
        }

        // Validación de la contraseña (mínimo 6 caracteres)
        if (usuario.getPassword() != null && usuario.getPassword().length() < 6) {
            errors.rejectValue("password", "field.password.tooShort", "La contraseña debe tener al menos 8 caracteres");
        }

    }
}