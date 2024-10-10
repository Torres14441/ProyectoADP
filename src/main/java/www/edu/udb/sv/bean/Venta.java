package www.edu.udb.sv.bean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
@Data
@Entity
@Table(name = "ventas")
public class Venta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta;

    @NotNull
    private int cantidadVendida;

    @Null
    private LocalDate fechaVenta;

    @NotEmpty
    private String nombreCliente;

    @NotEmpty
    private String DUI;

    @NotNull
    private BigDecimal total;

    // Relación ManyToOne con Producto
    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;

}
