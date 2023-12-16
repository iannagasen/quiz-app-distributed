import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationRequest, AuthenticationResponse } from '../model/core';
import { AuthenticationService } from '../service/authentication.service';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
  ],
  template: `
    <div class="min-h-screen flex items-center justify-center bg-clr-background">
      <div class="w-full max-w-md bg-white rounded-lg shadow-md p-8">
        <h2 class="text-3xl font-semibold text-center mb-6 text-clr-background">Login</h2>
        <form class="space-y-4" (ngSubmit)="onSubmit()" >
          <div>
            <label for="email" class="block text-sm font-semibold text-gray-700">Email</label>
            <input
              id="email" type="email" name="email"
              placeholder="Enter your email"
              class="w-full text-clr-primary font-bold rounded-md border-2 border-gray-300 px-3 py-2 focus:outline-none focus:border-clr-primary"
              [(ngModel)]="authenticationRequest.email"
            >
          </div>
          <div>
            <label for="password" class="block text-sm font-semibold text-gray-700">Password</label>
            <input
              id="password" type="password" name="password"
              placeholder="********"
              class="w-full text-clr-primary font-bold rounded-md border-2 border-gray-300 px-3 py-2 focus:outline-none focus:border-clr-primary"
              [(ngModel)]="authenticationRequest.password"
            >
          </div>
          <button class="w-full bg-clr-primary text-white font-semibold py-2 rounded-md transition duration-300 hover:bg-clr-accent focus:outline-none focus:bg-purple-800">
            Sign In
          </button>
        </form>
        <p class="text-gray-600 text-center mt-4">
          Don't have an account? <a href="#" class="text-clr-primary font-semibold hover:underline">Register here</a>.
        </p>
      </div>
    </div>

    <div *ngIf="errorMessage" class="fixed bottom-5 left-5 z-50 bg-red-500 text-white px-4 py-2 rounded-md shadow-md animate-shake">
      <span>{{ errorMessage }}</span>
      <button (click)="errorMessage = ''" class="ml-2 focus:outline-none">
        <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 fill-current" viewBox="0 0 20 20" fill="currentColor">
          <path fill-rule="evenodd" d="M10 0a10 10 0 1010 10A10.011 10.011 0 0010 0zM5.293 5.293a1 1 0 011.414 0L10 8.586l3.293-3.293a1 1 0 111.414 1.414L11.414 10l3.293 3.293a1 1 0 11-1.414 1.414L10 11.414l-3.293 3.293a1 1 0 01-1.414-1.414L8.586 10 5.293 6.707a1 1 0 010-1.414z" clip-rule="evenodd"/>
        </svg>
      </button>
    </div>
  `
})
export class LoginComponent implements OnInit {

  authenticationRequest: AuthenticationRequest = { email: '', password: '' }
  errorMessage = ''

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService,
  ) {}


  ngOnInit(): void {
    window.location.href = 
        `${environment.backendApi}/oauth2/authorize?response_type=code` +
        `&client_id=${environment.clientId}` +
        `&scope=${environment.scope}` +
        `&redirect_uri=${environment.redirectUri}` +
        `&state=123`
  }
  



  onSubmit() {
    // TODO: this will be delegated to the auth server, may be there will be a middle man component to 
    //    have a next button once the user go to the redirect uri
    // const subscription = { // or observer
    //   next: (response: AuthenticationResponse) => {
    //     if (response.data.token) {
    //       localStorage.setItem('token', response.data.token);
    //       localStorage.setItem('refresh', response.data.refresh);
    //       this.router.navigate(['dashboard']);
    //     } else {
    //       window.alert(response.data)
    //     }
    //   },

    //   error: (error: any) => {
    //     console.log(error)
    //     this.errorMessage = 'Something went wrong. Please try again later.'
    //   }
    // }


    // this.authenticationService
    //   .login(this.authenticationRequest)
    //   .subscribe(subscription);
  }
}