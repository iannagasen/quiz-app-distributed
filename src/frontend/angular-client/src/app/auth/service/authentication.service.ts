import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthenticationRequest, AuthenticationResponse } from '../model/core';
import { environment } from '../../../environments/environment';


// TODO: maybe move this to constants
export const ACCESS_TOKEN = 'access_token'
export const REFRESH_TOKEN = 'refresh_token'

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


  retrieveToken(code: string) {
    let params = new URLSearchParams();
    params.append('grant_type', 'authorization_code')
    params.append('client_id', environment.clientId)
    params.append('redirect_uri', environment.redirectUri)
    params.append('code', code);

    const basicAuth = `${environment.clientId}:${environment.clientSecret}`
    const headers = new HttpHeaders({
      'Content-type': 'application/x-www-form-urlencoded; charset=utf-8',
      'Authorization': `Basic ${btoa(basicAuth)}`
    });

    this._http.post(`${environment.backendApi}/oauth2/token`, params.toString(), { headers: headers })
      .subscribe({
          next: (data) => {
            this.saveToken(data as AuthenticationResponse);
          },
          error: (err) => alert('Invalid Credentials')
      })
  }

  
  saveToken(token: AuthenticationResponse) {
    localStorage.setItem(ACCESS_TOKEN, token.access_token)
    localStorage.setItem(REFRESH_TOKEN, token.refresh_token)
    console.log('Obtained access token')
  }
  

  isLoggedIn() {
    if (typeof localStorage === 'undefined') return false;
    
    if (localStorage.getItem(ACCESS_TOKEN) === null || localStorage.getItem(ACCESS_TOKEN) === '') {
      return false;
    } else {
      return true;
    }
  }
  

  redirectToCustomAuthenticatorLogin() {
    window.location.href = 
      `${environment.backendApi}/oauth2/authorize?response_type=code` +
      `&client_id=${environment.clientId}` +
      `&scope=${environment.scope}` +
      `&redirect_uri=${environment.redirectUri}` +
      `&state=123`
  }
}