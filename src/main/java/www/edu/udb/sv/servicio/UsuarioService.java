package www.edu.udb.sv.servicio;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import www.edu.udb.sv.bean.Rol;
import www.edu.udb.sv.bean.Usuario;
import www.edu.udb.sv.dao.RolDao;
import www.edu.udb.sv.dao.UsuarioDao;
import www.edu.udb.sv.util.EncriptarPassword;
import java.util.List;

@Service("userDetailsService")
@Slf4j
public class UsuarioService implements UserDetailsService {
    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private RolDao rolDao;

    @Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findByUsername(username);

        if(usuario == null){
            throw new UsernameNotFoundException(username);
        }

        var roles = new ArrayList<GrantedAuthority>();

        for(Rol rol: usuario.getRoles()){
            roles.add(new SimpleGrantedAuthority(rol.getNombre()));
        }

        return new User(usuario.getUsername(), usuario.getPassword(), roles);
    }

    // Método para listar todas las personas
    public List<Usuario> listarUsuarios() {
        return usuarioDao.findAll();
    }

    public Usuario obtenerUsuarioporID(Long id){
        return usuarioDao.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Transactional
    public void registrarUsuarioConRol(Long id,String username, String password, String nombreRol) {
        // Crear nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(id);
        usuario.setUsername(username);
        usuario.setPassword(EncriptarPassword.encriptarPassword(password)); // Asegúrate de encriptar la contraseña

        // Inicializar la lista de roles si es necesario
        if (usuario.getRoles() == null) {
            usuario.setRoles(new ArrayList<>()); // Inicializa la lista si es null
        }

        // Crear y asignar el rol al usuario
        Rol rol = new Rol();
        rol.setNombre(nombreRol);

        // Guardar el rol
        rolDao.save(rol);

        // Asignar el rol al usuario
        usuario.getRoles().add(rol); // Agregar el rol a la lista de roles del usuario

        // Guardar el usuario
        usuarioDao.save(usuario); // Guarda el usuario después de asignarle el rol
    }

    @Transactional
    public void eliminar(Long id){
        usuarioDao.deleteById(id);
    }

}
