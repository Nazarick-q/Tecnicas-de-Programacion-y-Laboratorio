package com.example.festivosapi.controller;

import com.example.festivosapi.model.Festivo;
import com.example.festivosapi.service.FestivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/festivos")
@CrossOrigin(origins = "*")
public class FestivoController {

    @Autowired
    private FestivoService festivoService;

    @GetMapping("/es-festivo")
    public boolean esFestivo(@RequestParam String fecha) {
        return festivoService.esFestivo(LocalDate.parse(fecha));
    }

    @GetMapping("/listar/{anio}")
    public List<Festivo> listarFestivosDelAño(@PathVariable int anio) {
        return festivoService.listarFestivosDelAño(anio);
    }
}