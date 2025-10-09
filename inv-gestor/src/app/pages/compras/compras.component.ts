import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
interface Compra {
  producto: string;
  proveedor: string;
  cantidad: number;
  precioUnit: number;
  total: number;
  fecha: string;
  estado: 'Recibida' | 'Pendiente';
}

@Component({
  selector: 'app-compras',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './compras.component.html',
  styleUrl: './compras.component.scss'
})

export class ComprasComponent {
constructor(private router: Router) {}

  compras: Compra[] = [
    {
      producto: 'Laptop Dell',
      proveedor: 'Dell Colombia',
      cantidad: 5,
      precioUnit: 2200000,
      total: 11000000,
      fecha: '2024-01-10',
      estado: 'Recibida',
    },
    {
      producto: 'Monitor 24"',
      proveedor: 'Samsung Distribuidora',
      cantidad: 10,
      precioUnit: 700000,
      total: 7000000,
      fecha: '2024-01-12',
      estado: 'Pendiente',
    },
  ];

  get totalCompras() {
    return this.compras.reduce((acc, c) => acc + c.total, 0);
  }

  get totalOrdenes() {
    return this.compras.length;
  }

  get ordenesPendientes() {
    return this.compras.filter((c) => c.estado === 'Pendiente').length;
  }

  nuevaCompra() {
    this.router.navigate(['/compras/nueva']);
  }
}
