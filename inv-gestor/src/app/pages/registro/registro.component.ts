import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  standalone: true,
  imports: [FormsModule, CommonModule,  RouterModule],
  styleUrls: ['./registro.component.scss'],
})
export class RegistroComponent {
  correo = '';
  contrasena = '';
  empresa = '';
  nit = '';
  responsable = '';
  documento = '';

  subirLogo() {
    alert('Función para subir el logo (pendiente de implementación)');
  }

  registrarse() {
    console.log({
      correo: this.correo,
      contrasena: this.contrasena,
      empresa: this.empresa,
      nit: this.nit,
      responsable: this.responsable,
      documento: this.documento,
    });
    alert('Registro exitoso (simulado)');
  }
}
