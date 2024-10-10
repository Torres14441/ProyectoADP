package www.edu.udb.sv.util;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import www.edu.udb.sv.bean.Producto;

import java.math.BigDecimal;

public class ProductoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Producto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Producto producto = (Producto) target;

        // Validar campos vacíos
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "field.nombre.required", "El nombre del producto es obligatorio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "precio", "field.precio.required", "El precio es obligatorio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cantidad", "field.cantidad.required", "La cantidad es obligatoria");

        // Validar que el precio sea un número positivo
        if (producto.getPrecio() != null && producto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            errors.rejectValue("precio", "field.precio.invalid", "El precio debe ser mayor que cero");
        }

        // Validar que la cantidad sea mayor o igual a 0
        if (producto.getCantidad() < 0) {
            errors.rejectValue("cantidad", "field.cantidad.invalid", "La cantidad debe ser mayor o igual a 0");
        }
    }
}