import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {
  totalVentas = 5345000;
  productos = 4;
  comprasMes = 18000000;
  stockBajo = 0;

  ventasRecientes = [
    { producto: 'Laptop Dell', cliente: 'Juan Pérez', monto: 5000000, fecha: '2024-01-15' },
    { producto: 'Mouse Logitech', cliente: 'María García', monto: 225000, fecha: '2024-01-16' },
    { producto: 'Teclado Mecánico', cliente: 'Carlos López', monto: 120000, fecha: '2024-01-17' },
  ];
}
