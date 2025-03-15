package com.prueba.biblioteca.services;

import com.prueba.biblioteca.dto.MensajeResponse;
import com.prueba.biblioteca.models.Libro;
import com.prueba.biblioteca.repositories.LibroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LibroServiceTest {

    @Mock
    private LibroRepository libroRepository;

    @InjectMocks
    private LibroService libroService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerTodos_DeberiaRetornarListaDeLibros() {
        // Datos simulados
        Libro libro1 = new Libro(1L, "Java Básico", "Autor1", "Programación", 5, "ACTIVO");
        Libro libro2 = new Libro(2L, "Spring Boot", "Autor2", "Backend", 3, "ACTIVO");
        when(libroRepository.findAll()).thenReturn(Arrays.asList(libro1, libro2));

        // Ejecutar el servicio
        ResponseEntity<?> respuesta = libroService.obtenerTodos();

        // Verificaciones
        assertNotNull(respuesta.getBody());
        assertInstanceOf(List.class, respuesta.getBody());
        assertEquals(2, ((List<?>) respuesta.getBody()).size());
        verify(libroRepository, times(1)).findAll();
    }

    @Test
    void obtenerTodos_DeberiaRetornarMensajeSiNoHayLibros() {
        when(libroRepository.findAll()).thenReturn(List.of());

        ResponseEntity<?> respuesta = libroService.obtenerTodos();

        assertEquals(404, respuesta.getStatusCodeValue());
        assertInstanceOf(MensajeResponse.class, respuesta.getBody());
        assertEquals("No hay libros registrados", ((MensajeResponse) respuesta.getBody()).getMensaje());
    }

    @Test
    void obtenerPorId_DeberiaRetornarLibroSiExiste() {
        Libro libro = new Libro(1L, "Java Básico", "Autor1", "Programación", 5, "ACTIVO");
        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));

        ResponseEntity<?> respuesta = libroService.obtenerPorId(1L);

        assertNotNull(respuesta.getBody());
        assertInstanceOf(Libro.class, respuesta.getBody());
        assertEquals("Java Básico", ((Libro) respuesta.getBody()).getTitulo());
    }

    @Test
    void obtenerPorId_DeberiaRetornarMensajeSiNoExiste() {
        when(libroRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity<?> respuesta = libroService.obtenerPorId(99L);

        assertEquals(404, respuesta.getStatusCodeValue());
        assertInstanceOf(MensajeResponse.class, respuesta.getBody());
        assertEquals("Libro no encontrado", ((MensajeResponse) respuesta.getBody()).getMensaje());
    }
}
