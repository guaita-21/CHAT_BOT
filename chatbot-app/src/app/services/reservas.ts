import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Reserva } from '../model/reservas.model';


@Injectable({
  providedIn: 'root',
})
export class ReservaService {

  private baseURL = 'http://localhost:8080/api/reservas';

  constructor(private http: HttpClient) { }

  findAll(): Observable<Reserva[]> {
    return this.http.get<Reserva[]>(this.baseURL);
  }

  findOne(id: number): Observable<Reserva> {
    return this.http.get<Reserva>(`${this.baseURL}/${id}`);
  }

  save(reserva: Reserva): Observable<Reserva> {
    return this.http.post<Reserva>(this.baseURL, reserva);
  }

  update(id: number, reserva: Reserva): Observable<Reserva> {
    return this.http.put<Reserva>(`${this.baseURL}/${id}`, reserva);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseURL}/${id}`);
  }

}
