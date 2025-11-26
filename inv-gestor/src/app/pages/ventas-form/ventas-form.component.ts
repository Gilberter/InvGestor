import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { VentasFormService } from '../../services/ventas-form.service';

interface SaleItem {
  productId: number;
  quantity: number;
  price: number;
}

@Component({
  selector: 'app-ventas-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './ventas-form.component.html',
  // styleUrls: ['./ventas-form.component.css']
})
export class VentasFormComponent {

  clientName = '';
  productId?: number;
  quantity?: number;
  price?: number;

  saleItems: SaleItem[] = [];
  isLoading = false;

  constructor(private ventasService: VentasFormService) {}

  agregarItem() {
    if (!this.productId || !this.quantity || !this.price) {
      alert("Debe completar todos los campos del producto");
      return;
    }

    this.saleItems.push({
      productId: this.productId,
      quantity: this.quantity,
      price: this.price
    });

    this.productId = undefined;
    this.quantity = undefined;
    this.price = undefined;
  }

  eliminarItem(index: number) {
    this.saleItems.splice(index, 1);
  }

  registrarVenta() {
    if (!this.clientName || this.saleItems.length === 0) {
      alert("Debe ingresar cliente y al menos un producto");
      return;
    }

    this.isLoading = true;

    const request = {
      clientName: this.clientName,
      saleItems: this.saleItems
    };

    this.ventasService.saveSale(request).subscribe({
      next: (resp) => {
        alert("Venta registrada correctamente");
        this.isLoading = false;
        this.saleItems = [];
        this.clientName = '';
      },
      error: (err) => {
        console.error(err);
        alert("Error registrando la venta");
        this.isLoading = false;
      }
    });
  }
}
