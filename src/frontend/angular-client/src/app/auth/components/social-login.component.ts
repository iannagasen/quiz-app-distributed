import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { environment } from '../../../environments/environment';

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
            (click)="loginWithGoogle()"
          >
            Google
          </button>
          <button
            class="w-full bg-blue-500 text-white font-semibold py-3 rounded-md shadow-md transition duration-300 hover:bg-blue-600 focus:outline-none focus:bg-blue-600"
            (click)="loginWithMyBackend()"
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
export class SocialLoginComponent {


  loginWithMyBackend() {
    window.location.href = 
      `${environment.backendApi}/oauth2/authorize?response_type=code` +
      `&client_id=${environment.clientId}` +
      `&scope=${environment.scope}` +
      `&redirect_uri=${environment.redirectUri}` +
      `&state=123`
  }

  loginWithGoogle() {

  }
}
