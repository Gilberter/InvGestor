import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  username: string = '';
  password: string = '';
  isLoading: boolean = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  login() {
    if (!this.username || !this.password) {
      alert('Please enter both email and password');
      return;
    }

    this.isLoading = true;

    this.authService.login(this.username, this.password).subscribe({
      next: (token) => {
        this.isLoading = false;
        console.log('Login successful, token stored:', token);

        const roles = this.authService.getUserRoles();
        
        // Token is automatically stored by the service
        // Verify token is stored
        const storedToken = this.authService.getToken();
        console.log('Token stored in localStorage:', storedToken);
        
        // Redirect to dashboard
        if (roles.includes('OWNER') || roles.includes('ADMIN')) {
          this.router.navigate(['/dashboard']);
        } else if (roles.includes('EMPLOYED')) {
          this.router.navigate(['/compras-form']);
        } else {
          alert('User role not recognized.');
        }
      },
      error: (error) => {
        this.isLoading = false;
        console.error('Login failed:', error);
        alert('Login failed. Please check your credentials.');
      }
    });
  }
}
