import { Routes } from '@angular/router';
import { LoginComponent } from './auth/components/login.component';
import { RegistrationComponent } from './auth/components/registration.component';
import { DashboardComponent } from './dashboard.component';
import { AuthGuard } from './auth/service/permissions.service';
import { SocialLoginComponent } from './auth/components/social-login.component';

export const routes: Routes = [
  { path: '', 
    title: 'Login', 
    component: SocialLoginComponent },
  { path: 'login', 
    title: 'Login', 
    component: LoginComponent },
  { path: 'register', 
    title: 'Register', 
    component: RegistrationComponent },
  { path: 'dashboard', 
    title: 'dashboard', 
    component: DashboardComponent,
  },
];
