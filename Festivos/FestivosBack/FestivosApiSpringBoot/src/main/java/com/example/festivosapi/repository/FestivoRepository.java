package com.example.festivosapi.repository;

import com.example.festivosapi.model.Festivo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface FestivoRepository extends JpaRepository<Festivo, Long> {
    boolean existsByFecha(LocalDate fecha);
    List<Festivo> findByFechaBetween(LocalDate inicio, LocalDate fin);
}