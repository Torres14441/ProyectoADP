package www.edu.udb.sv.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import www.edu.udb.sv.bean.Producto;
public interface ProductoDao extends JpaRepository<Producto, Long>{

}
