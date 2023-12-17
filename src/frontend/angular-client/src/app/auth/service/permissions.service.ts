import { Injectable, inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from '@angular/router';
import { ACCESS_TOKEN } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class PermissionsService {

  constructor(
    private _router: Router
  ) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (localStorage.getItem(ACCESS_TOKEN) === null || localStorage.getItem(ACCESS_TOKEN) === '') {
      this._router.navigate(["/"])
      return false;
    } else {
      return true;
    }
  }
}


export const AuthGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  return inject(PermissionsService).canActivate(route, state)
}