package www.edu.udb.sv.controllers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import www.edu.udb.sv.bean.Categoria;
import www.edu.udb.sv.bean.Producto;
import www.edu.udb.sv.servicio.CategoriaService;
import www.edu.udb.sv.servicio.ProductoService;
import www.edu.udb.sv.util.ProductoValidator;

import javax.validation.Valid;
import java.util.List;
@Controller
@Slf4j
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired CategoriaService categoriaService;

    @GetMapping("/pro/productos")
    public String inicioProducto(Model model){
        var productos = productoService.LisatarProductos();
        model.addAttribute("productos", productos);
        return "Productos/indexPro";
    }

    @GetMapping("/pro/agregarProducto")
    public String agregar(Producto producto, Model model){
        // Obtener todas las categorías para el formulario
        List<Categoria> categorias = categoriaService.ListarCategorias();
        model.addAttribute("categorias", categorias);
        return "Productos/modificarPro";
    }

    @PostMapping("/pro/guardarProducto")
    public String guardarCategoria(@Valid Producto producto, BindingResult result,Model model, @RequestParam int idCategoria){
        ProductoValidator pro = new ProductoValidator();
        pro.validate(producto,result);

        if (result.hasErrors()){
            List<Categoria> categorias = categoriaService.ListarCategorias();
            model.addAttribute("producto",producto);
            model.addAttribute("categorias",categorias);
            return "Productos/modificarPro";
        }
        Long id = producto.getIdProducto();
        String nom = producto.getNombre();
        String pre = String.valueOf(producto.getPrecio());
        int can = producto.getCantidad();

        productoService.RegistrarProducto(id,nom,pre,can,idCategoria);
        return "redirect:/pro/productos";
    }

    @GetMapping("/pro/obtenerProducto/{id}")
    public String obtenerProducto(@PathVariable("id") Long idProducto, Model model){
        Producto producto = productoService.obtenerProductoPorId(idProducto);
        // Obtener todas las categorías para el formulario
        List<Categoria> categorias = categoriaService.ListarCategorias();
        model.addAttribute("categorias", categorias);
        model.addAttribute("producto", producto);
        return "Productos/modificarPro";
    }

    @GetMapping("/pro/eliminarProducto")
    public String eliminarProducto(@RequestParam Long idProducto){
        productoService.eliminarProducto(idProducto);
        return "redirect:/pro/productos";
    }

}
