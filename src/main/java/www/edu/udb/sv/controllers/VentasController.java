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
import www.edu.udb.sv.util.UsuarioValidator;
import www.edu.udb.sv.util.VentaValidator;

import javax.validation.Valid;
import java.util.List;
@Controller
@Slf4j
public class VentasController {

    @Autowired
    private VentaService ventaService;
    @Autowired
    private ProductoService productoService;

    @GetMapping("/ven/ventas")
    public String inicioVentas(Model model){
        var ventas = ventaService.listarventas();
        model.addAttribute("ventas", ventas);
        return "Ventas/indexVent";
    }

    @GetMapping("/ven/agregarVenta")
    public String agregar(Venta venta, Model model){
        venta = new Venta();
        List<Producto> productos = productoService.LisatarProductos();
        model.addAttribute("productos", productos);
        model.addAttribute("venta",venta);
        return "Ventas/modificarVen";
    }
/*
    @PostMapping("/ven/guardarVenta")
    public String guardarVenta(@Valid Venta venta, BindingResult result, Model model ,@RequestParam int idProducto){
        VentaValidator vent = new VentaValidator();
        vent.validate(venta,result);

        if(result.hasErrors()){
            List<Producto> productos = productoService.LisatarProductos();
            model.addAttribute("venta",venta);
            model.addAttribute("productos", productos);
            return "Ventas/modificarVen";
        }
        Long id = venta.getIdVenta();
        int cant = venta.getCantidadVendida();
        String nom = venta.getNombreCliente();
        String DUI = venta.getDUI();

        ventaService.registrarVenta(id,idProducto,cant,nom,DUI);

        return "redirect:/ven/ventas";
    }

 */



    @PostMapping("/ven/guardarVenta")
   public String guardarVenta(@RequestParam Long idVenta,@RequestParam int idProducto, @RequestParam int cantidadVendida, @RequestParam String nombreCliente, @RequestParam String DUI){
       ventaService.registrarVenta(idVenta,idProducto,cantidadVendida,nombreCliente,DUI);
       return "redirect:/ven/ventas";
   }


    @GetMapping("/ven/obtenerVenta/{id}")
    public String obtenerVenta(@PathVariable("id") Long idVenta, Model model){
        Venta venta = ventaService.obtenerVentaporID(idVenta);
        //Obtener productos para el formulario
        List<Producto> productos = productoService.LisatarProductos();
        model.addAttribute("productos",productos);
        model.addAttribute("venta",venta);
        return "Ventas/modificarVen";
    }

    @GetMapping("/ven/eliminarVenta")
    public String eliminarVenta(@RequestParam Long idVenta){
        ventaService.eliminarVenta(idVenta);
        return "redirect:/ven/ventas";
    }


}
