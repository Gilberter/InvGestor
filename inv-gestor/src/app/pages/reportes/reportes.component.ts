import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BaseChartDirective } from 'ng2-charts';
import { ChartData } from 'chart.js';

@Component({
  selector: 'app-reportes',
  standalone: true,
  imports: [CommonModule, FormsModule, BaseChartDirective],
  templateUrl: './reportes.component.html',
  styleUrls: ['./reportes.component.scss']
})
export class ReportesComponent {

  fromDate = '2024-01-01';
  toDate = '2024-12-31';
  typeReport = 'ventas';

  // MÉTRICAS
  metrics = {
    ingresos: 5345000,
    ordenes: 3,
    promedio: 1781666.67,
    margen: -236.8
  };

  // LINE CHART
  lineChartData: ChartData<'line'> = {
    labels: ['2024-01'],
    datasets: [
      {
        label: 'Ingresos Mensuales',
        data: [4800000],
        tension: 0.3,
        borderColor: '#2563eb',
        pointBackgroundColor: '#2563eb',
        fill: false
      }
    ]
  };

  // BAR CHART
  barChartData: ChartData<'bar'> = {
    labels: ['Laptop Dell', 'Mouse Logitech', 'Teclado Mecánico'],
    datasets: [
      {
        label: 'Ventas',
        data: [4700000, 200000, 150000],
        backgroundColor: '#10b981'
      }
    ]
  };

  // PIE CHART
  pieChartData: ChartData<'pie'> = {
    labels: ['Electrónicos', 'Accesorios'],
    datasets: [
      {
        data: [94, 6],
        backgroundColor: ['#2563eb', '#10b981']
      }
    ]
  };

}
