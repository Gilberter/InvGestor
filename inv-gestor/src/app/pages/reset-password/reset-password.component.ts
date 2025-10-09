import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';

@Component({
  selector: 'app-reset-password',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent {
  correo = '';

  constructor(private router: Router) {}

  cancelar() {
    this.router.navigate(['/login']);
  }

  restablecer() {
    if (!this.correo) {
      alert('Por favor, ingrese su correo electrónico.');
      return;
    }

    console.log('Restablecer contraseña para:', this.correo);
    alert('Se ha enviado un enlace de restablecimiento (simulado).');
  }
}
