package www.edu.udb.sv.servicio;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import www.edu.udb.sv.bean.Producto;
import www.edu.udb.sv.bean.Venta;
import www.edu.udb.sv.dao.ProductoDao;
import www.edu.udb.sv.dao.UsuarioDao;
import www.edu.udb.sv.dao.VentaDao;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class VentaService {

    @Autowired
    ProductoDao productoDao;

    @Autowired
    UsuarioDao usuarioDao;

    @Autowired
    VentaDao ventaDao;

    public List<Venta> listarventas(){
        return ventaDao.findAll();
    }

    public Venta obtenerVentaporID(Long id){
        return ventaDao.findById(id).orElseThrow(() -> new RuntimeException("Venta no encontrada"));
    }

    @Transactional
    public void registrarVenta(Long idVenta, int idProducto, int cantidad, String NombreCliente, String DUI){
       Producto producto = productoDao.findById((long) idProducto).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
       Long id = producto.getIdProducto();
       int cant = producto.getCantidad();
       int Ncant = cant - cantidad;
       producto.setIdProducto(id);
       producto.setCantidad(Ncant);
       BigDecimal precio = producto.getPrecio();
       BigDecimal cantidadProducto = new BigDecimal(cantidad);
       BigDecimal TotalVendido = precio.multiply(cantidadProducto);
       Venta venta = new Venta();
       venta.setIdVenta(idVenta);
       venta.setProducto(producto);
       venta.setCantidadVendida(cantidad);
       venta.setTotal(TotalVendido);
       venta.setNombreCliente(NombreCliente);
       venta.setDUI(DUI);

       productoDao.save(producto);

       ventaDao.save(venta);
    }

    @Transactional
    public void eliminarVenta(Long id){
        ventaDao.deleteById(id);
    }

}
