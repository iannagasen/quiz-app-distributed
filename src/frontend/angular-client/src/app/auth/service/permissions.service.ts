import { Injectable, inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, RouterStateSnapshot } from '@angular/router';
import { Router } from 'express';
import { ACCESS_TOKEN } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class PermissionsService {

  constructor(
    private router: Router
  ) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (localStorage.getItem(ACCESS_TOKEN) === null || localStorage.getItem(ACCESS_TOKEN) === '') {
      return false;
    } else {
      return true;
    }
  }
}


export const AuthGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  return inject(PermissionsService).canActivate(route, state)
}