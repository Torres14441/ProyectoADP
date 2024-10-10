package www.edu.udb.sv.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import www.edu.udb.sv.bean.Rol;

public interface RolDao extends JpaRepository<Rol, Long> {
}
