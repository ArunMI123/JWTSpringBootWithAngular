import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProjectRoutingModule } from './project-routing.module';
import { TransactionListComponent } from './components/transaction/transaction-list/transaction-list.component';
import { ChartComponent } from './components/charts/chart/chart.component';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { AgmCoreModule } from '@agm/core';
import { NgxLoadingModule, ngxLoadingAnimationTypes } from 'ngx-loading';
import { MapComponent } from './components/maps/map/map.component';
import { TransationChildComponent } from './components/transaction/transaction-list/transation-child/transation-child.component';
import { CreateComponent } from './components/employee/create/create.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    TransactionListComponent,
    ChartComponent,
    MapComponent,
    TransationChildComponent,
    CreateComponent],
  imports: [
    CommonModule,
    ProjectRoutingModule,
    NgxChartsModule,
    FormsModule,
    NgxLoadingModule.forRoot({
      animationType: ngxLoadingAnimationTypes.wanderingCubes,
      backdropBackgroundColour: 'rgba(0,0,0,0.1)',
      backdropBorderRadius: '4px',
      primaryColour: '#ffffff',
      secondaryColour: '#ffffff',
      tertiaryColour: '#ffffff'
    }),
    AgmCoreModule.forRoot({
      // please get your own API key here:
      // https://developers.google.com/maps/documentation/javascript/get-api-key?hl=en
      apiKey: 'AIzaSyAnKnfq4Xp-xuve5O77m0Li7IPkmxqbWOs'
    })
  ]
})
export class ProjectModule {

}
