import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './components/home/home.component';
import { AlertsComponent } from './components/alerts/alerts.component';
import { ReportsComponent } from './components/reports/reports.component';
import { SettingsComponent } from './components/settings/settings.component';
import { SourcesComponent } from './components/sources/sources.component';

const routes : Routes = [
  { path:'', redirectTo:'/home', pathMatch:'full'},
  { path:'home', component:HomeComponent},
  { path:'alerts', component:AlertsComponent},
  { path:'reports', component:ReportsComponent},
  { path:'sources', component:SourcesComponent},
  { path:'settings', component:SettingsComponent},
]

@NgModule({
  imports : [ RouterModule.forRoot( routes, { useHash: true } ) ] ,
  exports : [ RouterModule ]
})

export class AppRouter{ }
