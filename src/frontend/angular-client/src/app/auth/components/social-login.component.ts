import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { environment } from '../../../environments/environment';
import { AuthenticationService } from '../service/authentication.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-social-login',
  standalone: true,
  imports: [
    CommonModule
  ],
  template: `
    <div class="min-h-screen flex items-center justify-center bg-gradient-to-br from-purple-900 to-indigo-900">
      <div class="w-full max-w-md bg-white rounded-lg shadow-xl p-8">
        <h2 class="text-4xl font-extrabold text-center mb-8 text-gray-900">Choose Login Method</h2>
        <div class="space-y-4">
          <button
            class="w-full bg-red-500 text-white font-semibold py-3 rounded-md shadow-md transition duration-300 hover:bg-red-600 focus:outline-none focus:bg-red-600"
            (click)="redirectToGoogleLogin()"
          >
            Google
          </button>
          <button
            class="w-full bg-blue-500 text-white font-semibold py-3 rounded-md shadow-md transition duration-300 hover:bg-blue-600 focus:outline-none focus:bg-blue-600"
            (click)="redirectToCustomAuthenticatorLogin()"
          >
            Custom Authenticator
          </button>
        </div>
        <p class="text-gray-600 text-center mt-8">
          Don't have an account? <a href="#" class="text-blue-400 font-semibold hover:underline">Register here</a>.
        </p>
      </div>
    </div>
  `
})
export class SocialLoginComponent implements OnInit {
  public isLoggedIn = false;


  constructor(
    private _authService: AuthenticationService,
    private _router: Router,
    private _activatedRoute: ActivatedRoute
  ) { }


  ngOnInit() {
    this.isLoggedIn = this._authService.isLoggedIn();

    if (this.isLoggedIn) {
      console.log("logged in")
      this._router.navigate(["/dashboard"]);
    } else {
      this._activatedRoute.queryParams.subscribe((params) => {
        if (params?.['code']) {
          this._authService.retrieveToken(params['code']);
        }
      })

      // TODO: maybe extract this if, and chain it reactive style
      if (this._authService.isLoggedIn()) {
        this._router.navigate(["/dashboard"]);
      }
    }
  }


  redirectToCustomAuthenticatorLogin() {
    console.log("click custom login")
    this._authService.redirectToCustomAuthenticatorLogin();
  }

  redirectToGoogleLogin() {
    // unimplemented
  }
}
