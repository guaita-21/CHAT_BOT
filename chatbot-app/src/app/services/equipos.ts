import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Equipo } from '../model/equipos.model';

@Injectable({
  providedIn: 'root',
})
export class EquiposService {

  private baseURL = "http://localhost:8080/api/equipos"

  constructor(private http: HttpClient){ }

    findAll(): Observable<Equipo[]>{
      return this.http.get<Equipo[]>(this.baseURL);
    }

    findOne(id: number): Observable<Equipo>{
      return this.http.get<Equipo>(`${this.baseURL}/${id}`);
    }

    save(equipos: Equipo): Observable<Equipo>{
      return this.http.post<Equipo>(this.baseURL, equipos);
    } 

    update(id: number, equipos: Equipo): Observable<Equipo>{
      return this.http.put<Equipo>(`${this.baseURL}/${id}`, equipos);
    } 

    delete(id: number): Observable<void>{
      return this.http.delete<void>(`${this.baseURL}/${id}`);
    }
  
}
