import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { EmployedService } from '../../services/employed.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register-employed',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './register-employed.component.html',
  // styleUrls: ['./register-employed.component.scss']
})
export class RegisterEmployedComponent {

  correo = '';
  contrasena = '';
  nombre = '';
  apellido = '';
  isLoading = false;

  constructor(
    private employedService: EmployedService,
    private router: Router
  ) {}

  registrarEmpleado() {

    if (!this.correo || !this.contrasena || !this.nombre || !this.apellido) {
      alert("Todos los campos obligatorios deben estar completos.");
      return;
    }

    this.isLoading = true;

    const data = {
      email: this.correo,
      password: this.contrasena,
      firstName: this.nombre,
      lastName: this.apellido
    };

    this.employedService.registerEmployed(data).subscribe({
      next: (res) => {
        console.log('Empleado registrado:', res);
        alert('Empleado registrado correctamente.');
        this.isLoading = false;
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        console.error("Error:", err);
        alert("No se pudo registrar el empleado.");
        this.isLoading = false;
      }
    });
  }
}
