// registro.component.ts
import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { RegistrationService, BusinessRegisterRequest } from '../../services/registration.service';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule],
  styleUrls: ['./registro.component.scss'],
})
export class RegistroComponent {
  correo = '';
  contrasena = '';
  empresa = '';
  nit = '';
  responsable = '';
  documento = '';
  isLoading = false;

  constructor(
    private registrationService: RegistrationService,
    private router: Router
  ) {}

  subirLogo() {
    alert('Función para subir el logo (pendiente de implementación)');
  }

  registrarse() {
    // Basic validation
    if (!this.validateForm()) {
      return;
    }

    this.isLoading = true;

    const registerData: BusinessRegisterRequest = {
      email_responsible: this.correo,
      password: this.contrasena,
      bussiness_name: this.empresa,
      id_tributaria: Number(this.nit),
      name_responsible: this.responsable,
      cc_responsible: this.documento
    };

    console.log('Sending registration data:', registerData);

    this.registrationService.registerBusiness(registerData).subscribe({
      next: (response) => {
        this.isLoading = false;
        console.log('Registration successful:', response);
        alert('Registro exitoso! Ahora puedes iniciar sesión.');
        this.router.navigate(['/login']);
      },
      error: (error) => {
        this.isLoading = false;
        console.error('Registration failed:', error);
        
        let errorMessage = 'Error en el registro. Por favor intenta nuevamente.';
        
        if (error.status === 400) {
          errorMessage = 'Datos inválidos. Verifica la información ingresada.';
        } else if (error.status === 409) {
          errorMessage = 'El correo o NIT ya están registrados.';
        } else if (error.error && error.error.message) {
          errorMessage = error.error.message;
        }
        
        alert(errorMessage);
      }
    });
  }

  private validateForm(): boolean {
    if (!this.correo || !this.contrasena || !this.empresa || !this.nit || !this.responsable || !this.documento) {
      alert('Por favor completa todos los campos.');
      return false;
    }

    // Email validation
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(this.correo)) {
      alert('Por favor ingresa un correo electrónico válido.');
      return false;
    }

    // NIT validation (should be a number)
    if (isNaN(Number(this.nit))) {
      alert('El NIT debe ser un número válido.');
      return false;
    }

    // Password length validation
    if (this.contrasena.length < 6) {
      alert('La contraseña debe tener al menos 6 caracteres.');
      return false;
    }

    return true;
  }
}