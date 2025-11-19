// registration.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface BusinessRegisterRequest {
  email_responsible: string;
  password: string;
  bussiness_name: string;
  id_tributaria: number;
  name_responsible: string;
  cc_responsible: string;
}

export interface BusinessRegisterResponse {
  id: number;
  email_responsible: string;
  bussiness_name: string;
  id_tributaria: number;
  name_responsible: string;
  cc_responsible: string;
}

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  private apiUrl = 'http://localhost:8080/home';

  constructor(private http: HttpClient) { }

  registerBusiness(registerData: BusinessRegisterRequest): Observable<BusinessRegisterResponse> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.post<BusinessRegisterResponse>(
      `${this.apiUrl}/register-business`, 
      registerData, 
      { headers }
    );
  }
}