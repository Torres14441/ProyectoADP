package www.edu.udb.sv.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import www.edu.udb.sv.bean.Categoria;
public interface CategoriaDao extends JpaRepository< Categoria, Long> {

}
