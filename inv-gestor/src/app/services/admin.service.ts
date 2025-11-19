// admin.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private adminUrl = 'http://localhost:8080/admin';

  constructor(private http: HttpClient) { }

  // Test endpoint - this should be protected with @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
  getProtectedData(): Observable<any> {
    return this.http.get(`${this.adminUrl}/test`);
  }

  // Another protected endpoint example
  getAdminData(): Observable<any> {
    return this.http.get(`${this.adminUrl}/data`);
  }
}