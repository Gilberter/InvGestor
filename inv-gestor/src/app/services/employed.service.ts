import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmployedService {

  private apiUrl = 'http://localhost:8080/admin';

  constructor(private http: HttpClient) {}

  registerEmployed(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, data);
  }
}
