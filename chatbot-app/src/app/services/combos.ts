import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Combos } from '../model/combos.model';

@Injectable({ providedIn: 'root' })
export class CombosService {

  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  findAll(): Observable<Combos[]> {
    return this.http.get<Combos[]>(`${this.baseUrl}/combos`);
  }

  save(combo: Combos): Observable<any> {
    return this.http.post(`${this.baseUrl}/combos`, combo);
  }

  update(id: number, combo: Combos): Observable<any> {
    return this.http.put(`${this.baseUrl}/combos/${id}`, combo);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/combos/${id}`);
  }

  // ✅ REUTILIZA el endpoint que ya te funciona
  subirImagen(formData: FormData): Observable<any> {
    return this.http.post(`${this.baseUrl}/upload-portada`, formData);
  }
}