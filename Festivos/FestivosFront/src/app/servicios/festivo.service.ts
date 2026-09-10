import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
@Injectable({ providedIn: 'root' })
export class FestivoService {
  private url = 'http://localhost:8080/festivos';
  constructor(private http: HttpClient) {}
  verificarFestivo(fecha: string): Observable<string> {
    return this.http.get(`${this.url}/verificar/${fecha}`, { responseType: 'text' });
  }
  obtenerFestivos(anio: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.url}/listar/${anio}`);
  }
}