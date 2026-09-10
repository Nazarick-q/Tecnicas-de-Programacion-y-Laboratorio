import { Component } from '@angular/core';
import { FestivoService } from 'src/app/servicios/festivo.service';
import { Festivo } from 'src/app/entidades/festivo';

@Component({
  selector: 'app-festivo',
  templateUrl: './festivo.component.html',
  styleUrls: ['./festivo.component.css']
})
export class FestivoComponent {
  fecha: string = '';
  anio: number = new Date().getFullYear();
  esFestivo: boolean | null = null;
  festivos: Festivo[] = [];

  constructor(private festivoService: FestivoService) {}

  verificarFestivo() {
    this.festivoService.esFestivo(this.fecha).subscribe(resp => {
      this.esFestivo = resp;
    }, error => {
      alert("Error al verificar la fecha");
    });
  }

  listarFestivos() {
    this.festivoService.listarFestivos(this.anio).subscribe(data => {
      this.festivos = data;
    }, error => {
      alert("Error al consultar los festivos");
    });
  }
}