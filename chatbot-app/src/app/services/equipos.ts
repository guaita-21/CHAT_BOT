import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Equipo } from '../model/equipos.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EquiposService {

  private url = 'http://localhost:8080/api/equipos';

  constructor(private http: HttpClient) { }

  findAll(): Observable<Equipo[]> {
    //console.log('Llamando a findAll() en EquiposService', this.http.get<Equipo[]>(this.url));
    return this.http.get<Equipo[]>(this.url);
  }

  save(equipo: any): Observable<any> {
    return this.http.post(this.url, equipo);
  }

  update(id: number, equipo: Equipo): Observable<any> {
    return this.http.put(`${this.url}/${id}`, equipo);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.url}/${id}`);
  }

  subirImagen(formData: FormData): Observable<any> {
    return this.http.post(`http://localhost:8080/api/upload-portada`, formData);
  }
}