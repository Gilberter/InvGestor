// role.guard.ts
import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const roleGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  // roles definidos en la ruta
  const requiredRoles = route.data?.['roles'] as string[];

  // 1. Si no hay token → login
  if (!authService.isLoggedIn()) {
    router.navigate(['/login']);
    return false;
  }

  // 2. Si la ruta tiene roles y no los cumple → acceso denegado
  if (requiredRoles && !authService.hasRequiredRole(requiredRoles)) {
    router.navigate(['/access-denied']); // o "access-denied"
    return false;
  }

  return true;
};
