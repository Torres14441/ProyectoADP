package www.edu.udb.sv.controllers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import www.edu.udb.sv.bean.Usuario;
import www.edu.udb.sv.servicio.UsuarioService;
import org.springframework.validation.BindingResult;
import www.edu.udb.sv.util.UsuarioValidator;

import javax.validation.Valid;

@Controller
@Slf4j
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public String inicio(Model model, @AuthenticationPrincipal User user){
        return "LandingPage";
    }

    //-------------------------------------------Usuarios---------------------------------------------------------------
    @GetMapping("/users/Usuarios")
    public String inicioUsuarios(Model model){
        var usuarios = usuarioService.listarUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "Usuarios/index";
    }
    @GetMapping("/users/agregar")
    public String agregar(Usuario usuario){
        return "Usuarios/modificar";
    }

    @PostMapping("/users/guardarUsuario")
    public String guardarusuario(@Valid Usuario usuario, BindingResult result , Model model, @RequestParam String rol){

        UsuarioValidator usu = new UsuarioValidator();
        usu.validate(usuario,result);

        if (result.hasErrors()){
            model.addAttribute("usuario",usuario);
            return "Usuarios/modificar";
        }

        usuarioService.registrarUsuarioConRol(usuario.getIdUsuario(),usuario.getUsername(),usuario.getPassword(),rol);
        return "redirect:/users/Usuarios";
    }

    @GetMapping("/users/obtenerUsuario/{id}")
    public String obtenerUsuario(@PathVariable("id") Long idUsuario, Model model){
        Usuario usuario = usuarioService.obtenerUsuarioporID(idUsuario);
        model.addAttribute("usuario",usuario);
        return "Usuarios/modificar";
    }

    @GetMapping("/users/eliminarUsuario")
    public String eliminarUsuario(@RequestParam Long idUsuario){
        usuarioService.eliminar(idUsuario);
        return "redirect:/users/Usuarios";
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Nombre del template para login
    }

    @GetMapping("/errores/403")
    public String accessDenied() {
        return "errores/403"; // Nombre del template para error 403
    }
}
