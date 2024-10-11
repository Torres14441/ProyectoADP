package www.edu.udb.sv.controllers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import www.edu.udb.sv.bean.Categoria;
import www.edu.udb.sv.bean.Producto;
import www.edu.udb.sv.bean.Usuario;
import www.edu.udb.sv.bean.Venta;
import www.edu.udb.sv.servicio.CategoriaService;
import www.edu.udb.sv.servicio.ProductoService;
import www.edu.udb.sv.servicio.UsuarioService;
import www.edu.udb.sv.servicio.VentaService;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;


import java.util.List;

@Controller
@Slf4j
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private VentaService ventaService;

    //------------------------------------------------------------------------------------------------------------------

    @GetMapping("/")
    public String inicio(Model model, @AuthenticationPrincipal User user){
        return "LandingPage";
    }

    //-------------------------------------------Usuarios---------------------------------------------------------------
    @GetMapping("/users/Usuarios")
    public String inicioUsuarios(Model model){
        var usuarios = usuarioService.listarUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "pages/index";
    }
    @GetMapping("/users/agregar")
    public String agregar(Usuario usuario){
        return "pages/modificar";
    }

    @PostMapping("/users/guardarUsuario")
    public String guardarusuario(@Valid Usuario usuario, BindingResult result , Model model, @RequestParam String rol){

        if (result.hasErrors()){
            model.addAttribute("usuario",usuario);
            return "pages/modificar";
        }

        usuarioService.registrarUsuarioConRol(usuario.getIdUsuario(),usuario.getUsername(),usuario.getPassword(),rol);
        return "redirect:users/Usuarios";
    }

    @GetMapping("/users/obtenerUsuario/{id}")
    public String obtenerUsuario(@PathVariable("id") Long idUsuario, Model model){
        Usuario usuario = usuarioService.obtenerUsuarioporID(idUsuario);
        model.addAttribute("usuario",usuario);
        return "pages/modificar";
    }

    @GetMapping("/users/eliminarUsuario")
    public String eliminarUsuario(@RequestParam Long idUsuario){
        usuarioService.eliminar(idUsuario);
        return "redirect:users/Usuarios";
    }

    //-----------------------------------------------------------------------------------------------------------------

    //-------------------------------------------Categorias-------------------------------------------------------------
        @GetMapping("/cat/categorias")
        public String inicioCategoria(Model model){
        var categorias = categoriaService.ListarCategorias();
        model.addAttribute("categorias", categorias);
        return "pages/indexCat";
        }

        @GetMapping("/cat/agregarcategoria")
        public String agregar(Categoria categoria){
        return "pages/modificarCat";
    }

        @PostMapping("/cat/guardarCategoria")
        public String guardarCategoria(@RequestParam Long idCategoria,@RequestParam String nombreCategoria, @RequestParam String descripcion){
           categoriaService.RegistrarCategoria(idCategoria ,nombreCategoria, descripcion);
           return "redirect:/cat/categorias";
        }

        @GetMapping("/cat/obtenerCtegoria/{id}")
        public String obtenerCategoria(@PathVariable("id") Long idCategoria, Model model){
            Categoria categoria = categoriaService.ObtenerCategoriaporID(idCategoria);
            model.addAttribute("categoria",categoria);
            return "pages/modificarCat";
        }

        @GetMapping("/cat/eliminarCategoria")
        public String eliminarCategoria(@RequestParam long idCategoria){
            categoriaService.EliminarCategoria(idCategoria);
            return "redirect:/cat/categorias";
        }

    //-----------------------------------------------------------------------------------------------------------------
    //-------------------------------------------Productos-------------------------------------------------------------

    @GetMapping("/pro/productos")
    public String inicioProducto(Model model){
        var productos = productoService.LisatarProductos();
        model.addAttribute("productos", productos);
        return "pages/indexPro";
    }

    @GetMapping("/pro/agregarProducto")
    public String agregar(Producto producto, Model model){
        // Obtener todas las categorías para el formulario
        List<Categoria> categorias = categoriaService.ListarCategorias();
        model.addAttribute("categorias", categorias);
        return "pages/modificarPro";
    }

    @PostMapping("/pro/guardarProducto")
    public String guardarCategoria(@RequestParam Long idProducto,@RequestParam String nombre, @RequestParam String precio, @RequestParam int cantidad, @RequestParam int idCategoria){
        productoService.RegistrarProducto(idProducto,nombre,precio,cantidad,idCategoria);
        return "redirect:/pro/productos";
    }

    @GetMapping("/pro/obtenerProducto/{id}")
    public String obtenerProducto(@PathVariable("id") Long idProducto, Model model){
        Producto producto = productoService.obtenerProductoPorId(idProducto);
        // Obtener todas las categorías para el formulario
        List<Categoria> categorias = categoriaService.ListarCategorias();
        model.addAttribute("categorias", categorias);
        model.addAttribute("producto", producto);
        return "pages/modificarPro";
    }

    @GetMapping("/pro/eliminarProducto")
    public String eliminarProducto(@RequestParam Long idProducto){
        productoService.eliminarProducto(idProducto);
        return "redirect:/pro/productos";
    }


    //-----------------------------------------------------------------------------------------------------------------
    //-------------------------------------------Ventas----------------------------------------------------------------
        @GetMapping("/ven/ventas")
        public String inicioVentas(Model model){
        var ventas = ventaService.listarventas();
        model.addAttribute("ventas", ventas);
        return "pages/indexVent";
        }

        @GetMapping("/ven/agregarVenta")
        public String agregar(Venta venta, Model model){
            venta = new Venta();
            List<Producto> productos = productoService.LisatarProductos();
            model.addAttribute("productos", productos);
            model.addAttribute("ventas",venta);
            return "pages/modificarVen";
        }

        @PostMapping("/ven/guardarVenta")
        public String guardarVenta(@RequestParam Long idVenta,@RequestParam int idProducto, @RequestParam int cantidad, @RequestParam String nombreCliente, @RequestParam String DUI){
            ventaService.registrarVenta(idVenta,idProducto,cantidad,nombreCliente,DUI);
            return "redirect:/ven/ventas";
        }

        @GetMapping("/ven/obtenerVenta/{id}")
        public String obtenerVenta(@PathVariable("id") Long idVenta, Model model){
        Venta venta = ventaService.obtenerVentaporID(idVenta);
        //Obtener productos para el formulario
            List<Producto> productos = productoService.LisatarProductos();
            model.addAttribute("productos",productos);
            model.addAttribute("ventas",venta);
            return "pages/modificarVen";
        }

        @GetMapping("/ven/eliminarVenta")
        public String eliminarVenta(@RequestParam Long idVenta){
            ventaService.eliminarVenta(idVenta);
            return "redirect:/ven/ventas";
        }


    //-----------------------------------------------------------------------------------------------------------------

    @GetMapping("/login")
    public String login() {
        return "login"; // Nombre del template para login
    }

    @GetMapping("/errores/403")
    public String accessDenied() {
        return "errores/403"; // Nombre del template para error 403
    }
}
