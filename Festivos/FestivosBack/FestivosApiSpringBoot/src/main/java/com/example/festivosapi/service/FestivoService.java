package com.example.festivosapi.service;

import com.example.festivosapi.model.Festivo;
import com.example.festivosapi.repository.FestivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Service
public class FestivoService {

    @Autowired
    private FestivoRepository festivoRepo;

    public boolean esFestivo(LocalDate fecha) {
        return festivoRepo.existsByFecha(fecha);
    }

    public List<Festivo> listarFestivosDelAño(int año) {
        LocalDate inicio = Year.of(año).atDay(1);
        LocalDate fin = Year.of(año).atMonth(12).atEndOfMonth();
        return festivoRepo.findByFechaBetween(inicio, fin);
    }
}