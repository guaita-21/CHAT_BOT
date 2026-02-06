import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Sede } from '../model/sedes.model';

@Injectable({
  providedIn: 'root',
})
export class SedesService {

  private baseURL = 'http://localhost:8080/api/sedes';

  constructor(private http: HttpClient) {}

  findAll(): Observable<Sede[]> {
    return this.http.get<Sede[]>(this.baseURL);
  }   

  findOne(id: number): Observable<Sede> {
    return this.http.get<Sede>(`${this.baseURL}/${id}`);
  }

  save(sede: Sede): Observable<Sede> {
    return this.http.post<Sede>(this.baseURL, sede);
  }

  update(id: number, sede: Sede): Observable<Sede> {
    return this.http.put<Sede>(`${this.baseURL}/${id}`, sede);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseURL}/${id}`);
  }
}
