package www.edu.udb.sv.util;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import www.edu.udb.sv.bean.Categoria;

public class CategoriaValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Categoria.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Categoria categoria = (Categoria) target;

        // Validar campos vacíos
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombreCategoria", "field.nombreCategoria.required", "El nombre de la categoría es obligatorio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descripcion", "field.descripcion.required", "La descripción es obligatoria");

        // Validar longitud del nombre de la categoría
        if (categoria.getNombreCategoria() != null && categoria.getNombreCategoria().length() < 3) {
            errors.rejectValue("nombreCategoria", "field.nombreCategoria.tooShort", "El nombre de la categoría debe tener al menos 3 caracteres");
        }
    }
}