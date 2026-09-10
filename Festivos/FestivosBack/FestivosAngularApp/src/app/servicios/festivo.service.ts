import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Festivo } from '../entidades/festivo';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FestivoService {

  private url = environment.urlAPI + 'festivos';

  constructor(private http: HttpClient) {}

  esFestivo(fecha: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.url}/es-festivo?fecha=${fecha}`);
  }

  listarFestivos(anio: number): Observable<Festivo[]> {
    return this.http.get<Festivo[]>(`${this.url}/listar/${anio}`);
  }
}