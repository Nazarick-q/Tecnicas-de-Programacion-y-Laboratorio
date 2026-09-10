package com.example.festivosapi.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Festivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    private String descripcion;

    // Getters y setters
}