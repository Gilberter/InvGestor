import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VentasFormService {

  private API_URL = "http://localhost:8080/sale-purchasing";

  constructor(private http: HttpClient) {}

  saveSale(data: any): Observable<any> {
    const token = localStorage.getItem("token");

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.post(`${this.API_URL}/sale-user`, data, { headers });
  }
}
