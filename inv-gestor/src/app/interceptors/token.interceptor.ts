// token.interceptor.ts
import { HttpInterceptorFn } from '@angular/common/http';

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  // Use the same key as in auth service
  const token = localStorage.getItem('authToken');

  console.log('Interceptor - Token found:', !!token); // Debug log

  if (token) {
    const newReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    console.log('Interceptor - Adding Authorization header'); // Debug log
    return next(newReq);
  }

  console.log('Interceptor - No token found'); // Debug log
  return next(req);
};