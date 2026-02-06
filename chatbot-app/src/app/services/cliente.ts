
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cliente } from '../model/cliente.model';

@Injectable({
  providedIn: 'root',
})
export class ClienteService {

  private baseURL = "http://localhost:8080/api/clientes"

  constructor(private http: HttpClient){ }

    findAll(): Observable<Cliente[]>{
      return this.http.get<Cliente[]>(this.baseURL);
    }

    findOne(id: number): Observable<Cliente>{
      return this.http.get<Cliente>(`${this.baseURL}/${id}`);
    }

    save(cliente: Cliente): Observable<Cliente>{
      return this.http.post<Cliente>(this.baseURL, cliente);
    } 

    update(id: number, cliente: Cliente): Observable<Cliente>{
      return this.http.put<Cliente>(`${this.baseURL}/${id}`, cliente);
    } 

    delete(id: number): Observable<void>{
      return this.http.delete<void>(`${this.baseURL}/${id}`);
    }

}

