import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ComprasFormService {

  private API_URL = "http://localhost:8080/api/purchasing";

  constructor(private http: HttpClient) {}

  savePurchasing(data: any): Observable<any> {

    const token = localStorage.getItem("token");

    const headers = new HttpHeaders({
      "Authorization": `Bearer ${token}`
    });

    return this.http.post(`${this.API_URL}/create`, data, { headers });
  }
}
