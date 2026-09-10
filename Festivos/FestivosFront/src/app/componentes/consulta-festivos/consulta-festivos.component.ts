import { Component } from '@angular/core';
import { FestivoService } from '../../servicios/festivo.service';
@Component({ selector: 'app-consulta-festivos', templateUrl: './consulta-festivos.component.html' })
export class ConsultaFestivosComponent {
  fecha = '';
  anio = '';
  resultado = '';
  festivos: any[] = [];
  constructor(private festivoService: FestivoService) {}
  consultarSiEsFestivo() {
    this.festivoService.verificarFestivo(this.fecha).subscribe(res => this.resultado = res);
  }
  consultarFestivosPorAnio() {
    this.festivoService.obtenerFestivos(this.anio).subscribe(res => this.festivos = res);
  }
}