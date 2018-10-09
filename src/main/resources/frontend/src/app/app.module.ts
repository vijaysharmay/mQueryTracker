import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { NavbarComponent } from './common/navbar/navbar.component';
import { SidebarComponent } from './common/sidebar/sidebar.component';
import { HomeComponent } from './components/home/home.component';

import { AppRouter } from './app.routes';
import { AlertsComponent } from './components/alerts/alerts.component';
import { ReportsComponent } from './components/reports/reports.component';
import { SettingsComponent } from './components/settings/settings.component';
import { SourcesComponent } from './components/sources/sources.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    SidebarComponent,
    HomeComponent,
    AlertsComponent,
    ReportsComponent,
    SettingsComponent,
    SourcesComponent
  ],
  imports: [
    BrowserModule,
    AppRouter,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
