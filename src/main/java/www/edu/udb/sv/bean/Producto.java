package www.edu.udb.sv.bean;
import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
@Entity
@Table(name = "productos")
public class Producto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    @NotEmpty
    private String nombre;

    @NotNull
    private BigDecimal precio;

    @NotNull
    private int cantidad;

    // Relación ManyToOne con Categoria
    @ManyToOne
    @JoinColumn(name = "idCategoria")
    private Categoria categoria;

}
