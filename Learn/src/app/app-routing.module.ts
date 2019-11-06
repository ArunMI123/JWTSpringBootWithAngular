import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
    {
      path: 'project',
      loadChildren: () => import('./Modules/project/project.module').then(mod => mod.ProjectModule)
    },
    {
      path: 'login',
      loadChildren: () => import('./Modules/login/login.module').then(mod => mod.LoginModule)
    },
    {
      path: '', redirectTo: 'login', pathMatch: 'full',
      data: { breadcrumb: 'Test list' }
    }
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
