import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Combos } from '../model/combos.model';

@Injectable({
  providedIn: 'root',
})
export class CombosService {

  private baseURL = "http://localhost:8080/api/combos"

  constructor(private http: HttpClient){ }

    findAll(): Observable<Combos[]>{
      return this.http.get<Combos[]>(this.baseURL);
    }

    findOne(id: number): Observable<Combos>{
      return this.http.get<Combos>(`${this.baseURL}/${id}`);
    }

    save(combos: Combos): Observable<Combos>{
      return this.http.post<Combos>(this.baseURL, combos);
    } 

    update(id: number, combos: Combos): Observable<Combos>{
      return this.http.put<Combos>(`${this.baseURL}/${id}`, combos);
    } 

    delete(id: number): Observable<void>{
      return this.http.delete<void>(`${this.baseURL}/${id}`);
    }

}


