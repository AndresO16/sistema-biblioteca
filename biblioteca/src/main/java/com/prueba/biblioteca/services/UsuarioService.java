package com.prueba.biblioteca.services;

import com.prueba.biblioteca.dto.MensajeResponse;
import com.prueba.biblioteca.models.Usuario;
import com.prueba.biblioteca.repositories.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public ResponseEntity<List<Usuario>> obtenerUsuariosActivos() {
        List<Usuario> usuariosActivos = usuarioRepository.findByEstado("ACTIVO");
        return ResponseEntity.ok(usuariosActivos);
    }

    public ResponseEntity<MensajeResponse> registrarUsuario(Usuario usuario) {
        usuario.setEstado("ACTIVO");
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(new MensajeResponse("Usuario registrado correctamente"));
    }

    public ResponseEntity<MensajeResponse> actualizarUsuario(Long id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setDireccion(usuarioActualizado.getDireccion());
            usuario.setEmail(usuarioActualizado.getEmail());
            usuarioRepository.save(usuario);
            return ResponseEntity.ok(new MensajeResponse("Usuario actualizado correctamente"));
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MensajeResponse("Usuario no encontrado")));
    }

    public ResponseEntity<MensajeResponse> eliminarUsuario(Long id) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setEstado("ELIMINADO");
            usuarioRepository.save(usuario);
            return ResponseEntity.ok(new MensajeResponse("Usuario eliminado correctamente"));
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MensajeResponse("Usuario no encontrado")));
    }
}
