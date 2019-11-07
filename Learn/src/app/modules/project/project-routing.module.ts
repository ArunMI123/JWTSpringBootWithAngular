import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TransactionListComponent } from './components/transaction/transaction-list/transaction-list.component';
import { ChartComponent } from './components/charts/chart/chart.component';
import { MapComponent } from './components/maps/map/map.component';
import { TransationChildComponent } from './components/transaction/transaction-list/transation-child/transation-child.component';
import { CreateComponent } from './components/employee/create/create.component';
import { AuthGuard } from 'src/app/auth/auth.guard';

export const ProjectRoutingRoutes: Routes = [
  { path: '', redirectTo: 'transactionlist', pathMatch: 'full' },
  {
    path: 'transactionlist', component: TransactionListComponent,
    data: { breadcrumb: 'Transaction list' },
    children: [
      {
        path: '**', component: TransationChildComponent,
        data: { breadcrumb: 'Test data' },
      }]
  },
  {
    path: 'chart', component: ChartComponent,
    data: { breadcrumb: 'Chart list' }
  },
  {
    path: 'map', component: MapComponent,
    data: { breadcrumb: 'Map list' }
  },
  {
    path: 'employee', component: CreateComponent,
    data: { breadcrumb: 'Create Component' },
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forChild(ProjectRoutingRoutes)],
  exports: [RouterModule]
})
export class ProjectRoutingModule { }
