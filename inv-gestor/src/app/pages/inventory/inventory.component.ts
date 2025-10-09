import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-inventory',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.scss']
})
export class InventoryComponent {
  products = [
    { name: 'Monitor LG', stock: 10 },
    { name: 'Teclado Mecánico', stock: 25 },
    { name: 'Mouse Logitech', stock: 40 }
  ];
}
