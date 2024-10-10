package www.edu.udb.sv.util;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import www.edu.udb.sv.bean.Venta;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class VentaValidator implements Validator {

    // Expresión regular para el formato de DUI (xxxxxxxx-x)
    private static final Pattern DUI_PATTERN = Pattern.compile("\\d{8}-\\d");

    @Override
    public boolean supports(Class<?> clazz) {
        return Venta.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Venta venta = (Venta) target;

        // Validar campos vacíos
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombreCliente", "field.nombreCliente.required", "El nombre del cliente es obligatorio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "DUI", "field.DUI.required", "El número de DUI es obligatorio");

        // Validar formato del DUI
        Matcher matcher = DUI_PATTERN.matcher(venta.getDUI());
        if (!matcher.matches()) {
            errors.rejectValue("DUI", "field.DUI.invalid", "El número de DUI no tiene el formato correcto (xxxxxxxx-x)");
        }

        // Validar cantidad mayor que 0
        if (venta.getCantidadVendida() <= 0) {
            errors.rejectValue("cantidad", "field.cantidad.invalid", "La cantidad debe ser mayor que 0");
        }

        // Validar total mayor que 0
        if (venta.getTotal() == null || venta.getTotal().compareTo(BigDecimal.ZERO) <= 0) {
            errors.rejectValue("total", "field.total.invalid", "El total debe ser mayor que 0");
        }
    }
}