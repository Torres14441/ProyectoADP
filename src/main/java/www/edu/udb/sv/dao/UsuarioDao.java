package www.edu.udb.sv.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import www.edu.udb.sv.bean.Usuario;

public interface UsuarioDao extends JpaRepository<Usuario, Long>{
    Usuario findByUsername(String username);
}
