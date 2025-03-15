package com.prueba.biblioteca.repositories;

import com.prueba.biblioteca.models.Prestamo;
import com.prueba.biblioteca.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByEstado(String estado);
    List<Prestamo> findByUsuarioAndEstado(Usuario usuario, String estado);
}
