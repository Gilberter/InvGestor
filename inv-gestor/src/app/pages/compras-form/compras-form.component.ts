import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ComprasFormService } from '../../services/compras-form.service';

interface PurchasingItem {
  productId: number;
  quantity: number;
  price: number;
}

@Component({
  selector: 'app-compras-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './compras-form.component.html',
  styleUrls: ['./compras-form.component.css']
})
export class ComprasFormComponent {

  supplierName = '';
  productId?: number;
  quantity?: number;
  price?: number;

  purchasingItems: PurchasingItem[] = [];
  isLoading = false;

  constructor(private comprasService: ComprasFormService) {}

  agregarItem() {
    if (!this.productId || !this.quantity || !this.price) {
      alert("Debe completar todos los campos del producto");
      return;
    }

    this.purchasingItems.push({
      productId: this.productId,
      quantity: this.quantity,
      price: this.price
    });

    // Reset campos de producto
    this.productId = undefined;
    this.quantity = undefined;
    this.price = undefined;
  }

  eliminarItem(index: number) {
    this.purchasingItems.splice(index, 1);
  }

  registrarCompra() {
    if (!this.supplierName || this.purchasingItems.length === 0) {
      alert("Debe ingresar proveedor y al menos un producto");
      return;
    }

    this.isLoading = true;

    const request = {
      supplierName: this.supplierName,
      purchasingItems: this.purchasingItems
    };

    this.comprasService.savePurchasing(request).subscribe({
      next: (resp) => {
        alert("Compra registrada correctamente");
        this.isLoading = false;
        this.purchasingItems = [];
        this.supplierName = '';
      },
      error: (err) => {
        console.error(err);
        alert("Error registrando la compra");
        this.isLoading = false;
      }
    });
  }
}
