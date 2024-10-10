package www.edu.udb.sv.servicio;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import www.edu.udb.sv.bean.Categoria;
import www.edu.udb.sv.bean.Producto;
import www.edu.udb.sv.dao.CategoriaDao;
import www.edu.udb.sv.dao.ProductoDao;

@Service
@Slf4j
public class ProductoService {

    @Autowired
    private ProductoDao productoDao;

    @Autowired
    private CategoriaDao categoriaDao;

    //Metodo para listar

    public List<Producto> LisatarProductos(){
        return productoDao.findAll();
    }

    public Producto obtenerProductoPorId(Long id) {
        return productoDao.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @Transactional
    public void RegistrarProducto(Long id, String nombre, String precio, int cantidad, int idCategoria){
        Producto producto = new Producto();
        producto.setIdProducto(id);
        producto.setNombre(nombre);
        BigDecimal precioconver = new BigDecimal(precio);
        producto.setPrecio(precioconver);
        producto.setCantidad(cantidad);
        // Buscar la categoría por su ID
        Categoria categoria = categoriaDao.findById((long) idCategoria).orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        producto.setCategoria(categoria);

        productoDao.save(producto);
    }

    @Transactional
    public void eliminarProducto(Long id){
        productoDao.deleteById(id);
    }
}
