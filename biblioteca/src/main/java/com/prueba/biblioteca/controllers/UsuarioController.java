package com.prueba.biblioteca.controllers;

import com.prueba.biblioteca.dto.MensajeResponse;
import com.prueba.biblioteca.models.Usuario;
import com.prueba.biblioteca.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Usuario>> obtenerUsuariosActivos() {
        return usuarioService.obtenerUsuariosActivos();
    }

    @PostMapping("/registrar")
    public ResponseEntity<MensajeResponse> registrarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.registrarUsuario(usuario);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<MensajeResponse> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.actualizarUsuario(id, usuario);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<MensajeResponse> eliminarUsuario(@PathVariable Long id) {
        return usuarioService.eliminarUsuario(id);
    }
}
