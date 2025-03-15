package com.prueba.biblioteca.repositories;

import com.prueba.biblioteca.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByEstado(String estado);
    List<Libro> findByTituloContainingIgnoreCaseAndEstado(String titulo, String estado);
}
