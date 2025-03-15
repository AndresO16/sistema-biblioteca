package com.prueba.biblioteca.services;

import com.prueba.biblioteca.dto.MensajeResponse;
import com.prueba.biblioteca.models.Libro;
import com.prueba.biblioteca.models.Prestamo;
import com.prueba.biblioteca.models.Usuario;
import com.prueba.biblioteca.repositories.LibroRepository;
import com.prueba.biblioteca.repositories.PrestamoRepository;
import com.prueba.biblioteca.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PrestamoServiceTest {
    @Mock
    private PrestamoRepository prestamoRepository;

    @Mock
    private LibroRepository libroRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private PrestamoService prestamoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarPrestamo_DeberiaFallarSiLibroNoTieneUnidades() {
        Libro libro = new Libro(1L, "Java Básico", "Autor1", "Programación", 0, "SIN UNIDADES");
        Usuario usuario = new Usuario(1L, "Juan", "12345678", "Calle 123", "juan@email.com", "ACTIVO");

        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        ResponseEntity<MensajeResponse> respuesta = prestamoService.registrarPrestamo(1L, 1L);

        assertEquals(400, respuesta.getStatusCodeValue());
        assertEquals("No hay unidades disponibles para prestar", respuesta.getBody().getMensaje());
    }

    @Test
    void registrarPrestamo_DeberiaFallarSiUsuarioEliminado() {
        Libro libro = new Libro(1L, "Java Básico", "Autor1", "Programación", 1, "ACTIVO");
        Usuario usuario = new Usuario(1L, "Juan", "12345678", "Calle 123", "juan@email.com", "ELIMINADO");

        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        ResponseEntity<MensajeResponse> respuesta = prestamoService.registrarPrestamo(1L, 1L);

        assertEquals(400, respuesta.getStatusCodeValue());
        assertEquals("No se puede prestar un libro a un usuario eliminado", respuesta.getBody().getMensaje());
    }

    @Test
    void devolverPrestamo_DeberiaActualizarEstadoSiExitoso() {
        Libro libro = new Libro(1L, "Java Básico", "Autor1", "Programación", 0, "SIN UNIDADES");
        Usuario usuario = new Usuario(1L, "Juan", "12345678", "Calle 123", "juan@email.com", "ACTIVO");
        Prestamo prestamo = new Prestamo(1L, libro, usuario, null, null, "PRESTADO");

        when(prestamoRepository.findById(1L)).thenReturn(Optional.of(prestamo));
        when(libroRepository.save(any())).thenReturn(libro);
        when(prestamoRepository.save(any())).thenReturn(prestamo);

        ResponseEntity<MensajeResponse> respuesta = prestamoService.devolverPrestamo(1L);

        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Préstamo devuelto correctamente", respuesta.getBody().getMensaje());
    }
}
