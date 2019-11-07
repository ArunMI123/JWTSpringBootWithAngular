import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { LoginRoutingModule } from './login-routing.module';
import { UserLoginComponent } from './components/user-login/user-login.component';
import { ToastModule } from 'primeng/toast';

@NgModule({
  declarations: [UserLoginComponent],
  imports: [
    CommonModule,
    LoginRoutingModule,
    FormsModule,
    ToastModule
  ]
})
export class LoginModule { }
