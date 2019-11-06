import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserLoginComponent } from './components/user-login/user-login.component';


const LoginRoutingRoutes: Routes = [
  {
    path: 'userlogin', component: UserLoginComponent,
    data: { breadcrumb: 'userlogin page' }
  },
  {
    path: '', redirectTo: 'userlogin', pathMatch: 'full',
    data: { breadcrumb: 'userlogin redirect' }
  }
];

@NgModule({
  imports: [RouterModule.forChild(LoginRoutingRoutes)],
  exports: [RouterModule]
})
export class LoginRoutingModule { }
