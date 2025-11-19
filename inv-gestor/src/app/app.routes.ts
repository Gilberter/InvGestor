// app.routes.ts
import { Routes } from '@angular/router';
import { LayoutComponent } from './layout/layout.component';
import { LoginComponent } from './pages/login/login.component';
import { RegistroComponent } from './pages/registro/registro.component';
import { ResetPasswordComponent } from './pages/reset-password/reset-password.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { VentasComponent } from './pages/ventas/ventas.component';
import { ProductosComponent } from './pages/productos/productos.component';
import { ComprasComponent } from './pages/compras/compras.component';
import { ReportesComponent } from './pages/reportes/reportes.component';
import { roleGuard } from './interceptors/role.guard';
import { AccessDeniedComponent } from './pages/access-denied/access-denied.component';
import { RegisterEmployedComponent } from './pages/register-employed/register-employed.component';
import { VentasFormComponent } from './pages/ventas-form/ventas-form.component';

// app.routes.ts
export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'registro', component: RegistroComponent },
  { path: 'reset-password', component: ResetPasswordComponent },

  {
    path: '',
    component: LayoutComponent,
    canActivate: [roleGuard],
    children: [

      // Roles OWNER + ADMIN
      {
        path: 'dashboard',
        component: DashboardComponent,
        canActivate: [roleGuard],
        data: { roles: ['OWNER', 'ADMIN'] }
      },
      {
        path: 'productos',
        component: ProductosComponent,
        canActivate: [roleGuard],
        data: { roles: ['OWNER', 'ADMIN', "EMPLOYED"] }
      },
      {
        path: 'reportes',
        component: ReportesComponent,
        canActivate: [roleGuard],
        data: { roles: ['OWNER', 'ADMIN'] }
      },

      // Roles EMPLOYED + OWNER + ADMIN
      {
        path: 'ventas',
        component: VentasComponent,
        canActivate: [roleGuard],
        data: { roles: ['EMPLOYED', 'OWNER', 'ADMIN'] }
      },
      {
        path: 'compras',
        component: ComprasComponent,
        canActivate: [roleGuard],
        data: { roles: ['EMPLOYED', 'OWNER', 'ADMIN'] }
      },
      {
        path: 'register-employed',
        component: RegisterEmployedComponent,
        canActivate: [roleGuard],
        data: { roles: ['OWNER', 'ADMIN'] }
      },
      {
        path: 'ventas-form',
        component: VentasFormComponent,
        canActivate: [roleGuard],
        data: { roles: ['EMPLOYED', 'OWNER', 'ADMIN'] }
      },
      {
        path: 'compras-form',
        component: ComprasComponent,
        canActivate: [roleGuard],
        data: { roles: ['EMPLOYED', 'OWNER', 'ADMIN'] }
      }
    ]
  },

  { path: 'access-denied', component: AccessDeniedComponent },

  { path: '**', redirectTo: 'login' }
];
