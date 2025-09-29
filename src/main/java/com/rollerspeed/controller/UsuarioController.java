package com.rollerspeed.controller;

import com.rollerspeed.model.Usuario;
import com.rollerspeed.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Mostrar formulario de registro
    @GetMapping("/nuevo")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "nuevo_usuario";
    }

    // Guardar nuevo usuario
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario,
                                 @RequestParam("rol") String rol,
                                 @RequestParam("clase") String clase) {
        // Generar username a partir del correo
        if (usuario.getCorreo() != null && usuario.getCorreo().contains("@")) {
            String username = usuario.getCorreo().split("@")[0];
            usuario.setUsername(username);
        }

        usuario.setRol(rol);
        usuario.setClase(clase);

        // Encriptar la contraseña antes de guardar
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }

        usuarioRepository.save(usuario);
        return "redirect:/usuarios/listar";
    }

    // Listar todos los usuarios
    @GetMapping("/listar")
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "usuarios";
    }

    // Eliminar usuario por id
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable("id") Long id) {
        usuarioRepository.deleteById(id);
        return "redirect:/usuarios/listar";
    }

    // Mostrar formulario de edición
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") Long id, Model model) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + id));
        model.addAttribute("usuario", usuario);
        return "editar_usuario";
    }

    // Actualizar usuario existente
    @PostMapping("/actualizar/{id}")
    public String actualizarUsuario(@PathVariable("id") Long id,
                                    @ModelAttribute Usuario usuario,
                                    @RequestParam("rol") String rol,
                                    @RequestParam("clase") String clase) {
        usuario.setId(id);
        usuario.setRol(rol);
        usuario.setClase(clase);

        // Generar username a partir del correo si es nuevo
        if (usuario.getCorreo() != null && usuario.getCorreo().contains("@")) {
            String username = usuario.getCorreo().split("@")[0];
            usuario.setUsername(username);
        }

        // Encriptar la contraseña si fue modificada
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        } else {
            Usuario existente = usuarioRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + id));
            usuario.setPassword(existente.getPassword());
        }

        usuarioRepository.save(usuario);
        return "redirect:/usuarios/listar";
    }

// Mostrar formulario de registro público
@GetMapping("/registro")
public String mostrarRegistroPublico(Model model) {
    model.addAttribute("usuario", new Usuario());
    return "nuevo_usuario"; // Reutiliza la misma vista que /nuevo
}

}
