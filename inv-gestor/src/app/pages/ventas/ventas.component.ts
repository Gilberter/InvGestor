import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';

interface Venta {
  producto: string;
  cliente: string;
  cantidad: number;
  precioUnit: number;
  total: number;
  fecha: string;
  estado: 'Completada' | 'Pendiente';
}

@Component({
  selector: 'app-ventas',
  templateUrl: './ventas.component.html',
  styleUrls: ['./ventas.component.scss'],
  standalone: true,
  imports: [CommonModule, RouterModule]
})
export class VentasComponent implements OnInit {

  ventas: Venta[] = [
    {
      producto: 'Laptop Dell',
      cliente: 'Juan Pérez',
      cantidad: 2,
      precioUnit: 2500000,
      total: 5000000,
      fecha: '2024-01-15',
      estado: 'Completada'
    },
    {
      producto: 'Mouse Logitech',
      cliente: 'María García',
      cantidad: 5,
      precioUnit: 45000,
      total: 225000,
      fecha: '2024-01-16',
      estado: 'Completada'
    },
    {
      producto: 'Teclado Mecánico',
      cliente: 'Carlos López',
      cantidad: 1,
      precioUnit: 120000,
      total: 120000,
      fecha: '2024-01-17',
      estado: 'Pendiente'
    }
  ];

  totalVentas = 0;
  ventasCompletadas = 0;

  ngOnInit(): void {
    this.calcularTotales();
  }

  calcularTotales() {
    this.totalVentas = this.ventas.reduce((acc, v) => acc + v.total, 0);
    this.ventasCompletadas = this.ventas.filter(v => v.estado === 'Completada').length;
  }
}
