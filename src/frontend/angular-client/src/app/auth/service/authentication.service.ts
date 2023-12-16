import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthenticationRequest, AuthenticationResponse } from '../model/core';
import { environment } from '../../../environments/environment';
import { PermissionsService } from './permissions.service';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {

  constructor(
    private _http: HttpClient,
  ) { }

  login(authenticationRequest: AuthenticationRequest) {
    return this._http.post<AuthenticationResponse>(`${environment.backendApi}/user/authenticate`, authenticationRequest);
  }

  isLoggedIn() {
    if (localStorage.getItem('token') === null || localStorage.getItem('token') === '') {
      return false;
    } else {
      return true;
    }
  }

}
