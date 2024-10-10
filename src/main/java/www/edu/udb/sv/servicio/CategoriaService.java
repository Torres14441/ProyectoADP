package www.edu.udb.sv.servicio;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import www.edu.udb.sv.bean.Categoria;
import www.edu.udb.sv.dao.CategoriaDao;

@Service
@Slf4j
public class CategoriaService{

    @Autowired
    private CategoriaDao categoriaDao;

    //Meotodo para listar categorías
    public List<Categoria> ListarCategorias(){
        return categoriaDao.findAll();
    }

    public Categoria ObtenerCategoriaporID(Long id){
       return categoriaDao.findById(id).orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
    }

    @Transactional
    public void RegistrarCategoria(Long id,String nombre, String descripcion){
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(id);
        categoria.setNombreCategoria(nombre);
        categoria.setDescripcion(descripcion);
        categoriaDao.save(categoria);
    }

    @Transactional
    public void EliminarCategoria(Long id){
        categoriaDao.deleteById(id);
    }
}
