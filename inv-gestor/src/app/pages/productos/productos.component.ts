import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';

interface Producto {
  nombre: string;
  categoria: string;
  descripcion: string;
  precio: number;
  stock: number;
}

@Component({
  selector: 'app-productos',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule
  ],
  templateUrl: './productos.component.html',
  styleUrls: ['./productos.component.scss']
})
export class ProductosComponent implements OnInit {


  productos: Producto[] = [
    {
      nombre: "Laptop Dell",
      categoria: "Electrónicos",
      descripcion: "Laptop Dell Inspiron 15",
      precio: 2500000,
      stock: 10
    },
    {
      nombre: "Mouse Logitech",
      categoria: "Accesorios",
      descripcion: "Mouse inalámbrico Logitech MX",
      precio: 45000,
      stock: 25
    },
    {
      nombre: "Teclado Mecánico",
      categoria: "Accesorios",
      descripcion: "Teclado mecánico RGB",
      precio: 120000,
      stock: 15
    },
    {
      nombre: "Monitor 24\"",
      categoria: "Electrónicos",
      descripcion: "Monitor LED 24 pulgadas Full HD",
      precio: 800000,
      stock: 8
    }
  ];

  totalUnidades = 0;
  totalValor = 0;
  productosStockBajo = 0;

  ngOnInit(): void {
    this.calcularResumen();
  }

  calcularResumen() {
    this.totalUnidades = this.productos.reduce((a, p) => a + p.stock, 0);
    this.totalValor = this.productos.reduce((a, p) => a + (p.precio * p.stock), 0);
    this.productosStockBajo = this.productos.filter(p => p.stock === 0).length;
  }

}
