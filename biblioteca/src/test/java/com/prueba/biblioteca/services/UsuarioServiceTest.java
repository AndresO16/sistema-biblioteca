package com.prueba.biblioteca.services;

import com.prueba.biblioteca.dto.MensajeResponse;
import com.prueba.biblioteca.models.Usuario;
import com.prueba.biblioteca.repositories.UsuarioRepository;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerUsuariosActivos_DeberiaRetornarSoloActivos() {
        Usuario usuario1 = new Usuario(1L, "Juan", "12345678", "Calle 123", "juan@email.com", "ACTIVO");
        Usuario usuario2 = new Usuario(2L, "Pedro", "87654321", "Avenida 456", "pedro@email.com", "ELIMINADO");

        when(usuarioRepository.findByEstado("ACTIVO")).thenReturn(Arrays.asList(usuario1));

        ResponseEntity<List<Usuario>> respuesta = usuarioService.obtenerUsuariosActivos();

        assertNotNull(respuesta.getBody());
        assertEquals(1, respuesta.getBody().size());
        assertEquals("Juan", respuesta.getBody().get(0).getNombre());
    }

    @Test
    void eliminarUsuario_DeberiaCambiarEstadoAEliminado() {
        Usuario usuario = new Usuario(1L, "Juan", "12345678", "Calle 123", "juan@email.com", "ACTIVO");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any())).thenReturn(usuario);

        ResponseEntity<MensajeResponse> respuesta = usuarioService.eliminarUsuario(1L);

        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Usuario eliminado correctamente", respuesta.getBody().getMensaje());
    }

    @Test
    void eliminarUsuario_DeberiaRetornarErrorSiNoExiste() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity<MensajeResponse> respuesta = usuarioService.eliminarUsuario(99L);

        assertEquals(404, respuesta.getStatusCodeValue());
        assertEquals("Usuario no encontrado", respuesta.getBody().getMensaje());
    }
}
