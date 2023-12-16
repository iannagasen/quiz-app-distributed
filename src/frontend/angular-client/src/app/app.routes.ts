import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/components/login.component';
import { NgModule } from '@angular/core';
import { RegistrationComponent } from './auth/components/registration.component';
import { DashboardComponent } from './dashboard.component';
import { AuthGuard } from './auth/service/permissions.service';

export const routes: Routes = [
  { path: '', 
    title: 'Login', 
    component: LoginComponent },
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
