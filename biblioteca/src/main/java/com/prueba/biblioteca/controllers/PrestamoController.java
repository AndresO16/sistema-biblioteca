package com.prueba.biblioteca.controllers;

import com.prueba.biblioteca.dto.MensajeResponse;
import com.prueba.biblioteca.services.PrestamoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {
    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @GetMapping("/total")
    public ResponseEntity<?> obtenerTodos() {
        return prestamoService.obtenerTodos();
    }

    @GetMapping("/activos")
    public ResponseEntity<?> obtenerPrestamosActivos() {
        return prestamoService.prestamosActivos();
    }

    @PostMapping("/prestar/{libroId}/{usuarioId}")
    public ResponseEntity<MensajeResponse> registrarPrestamo(@PathVariable Long libroId, @PathVariable Long usuarioId) {
        return prestamoService.registrarPrestamo(libroId, usuarioId);
    }

    @PutMapping("/devolver/{prestamoId}")
    public ResponseEntity<MensajeResponse> devolverPrestamo(@PathVariable Long prestamoId) {
        return prestamoService.devolverPrestamo(prestamoId);
    }
}
