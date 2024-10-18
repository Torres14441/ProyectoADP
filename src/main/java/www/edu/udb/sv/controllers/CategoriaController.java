package www.edu.udb.sv.controllers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import www.edu.udb.sv.bean.Categoria;
import www.edu.udb.sv.servicio.CategoriaService;
import www.edu.udb.sv.util.CategoriaValidator;

import javax.validation.Valid;

@Controller
@Slf4j
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;


    @GetMapping("/cat/categorias")
    public String inicioCategoria(Model model){
        var categorias = categoriaService.ListarCategorias();
        model.addAttribute("categorias", categorias);
        return "Categoria/indexCat";
    }

    @GetMapping("/cat/agregarcategoria")
    public String agregar(Categoria categoria){
        return "Categoria/modificarCat";
    }

    @PostMapping("/cat/guardarCategoria")
    public String guardarCategoria(@Valid Categoria categoria, BindingResult result, Model model){
        CategoriaValidator cat = new CategoriaValidator();
        cat.validate(categoria,result);

        if (result.hasErrors()){
            model.addAttribute("categoria",categoria);
            return "Categoria/modificarCat";
        }
        Long id = categoria.getIdCategoria();
        String nom = categoria.getNombreCategoria();
        String des = categoria.getDescripcion();

        categoriaService.RegistrarCategoria(id,nom,des);

        return "redirect:/cat/categorias";
    }

    @GetMapping("/cat/obtenerCtegoria/{id}")
    public String obtenerCategoria(@PathVariable("id") Long idCategoria, Model model){
        Categoria categoria = categoriaService.ObtenerCategoriaporID(idCategoria);
        model.addAttribute("categoria",categoria);
        return "Categoria/modificarCat";
    }

    @GetMapping("/cat/eliminarCategoria")
    public String eliminarCategoria(@RequestParam long idCategoria){
        categoriaService.EliminarCategoria(idCategoria);
        return "redirect:/cat/categorias";
    }

}
