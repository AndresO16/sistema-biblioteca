package com.prueba.biblioteca.models;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String direccion;

    @Column(unique = true, nullable = false)
    private String cedula;

    @Column(nullable = false)
    private String estado;
}
