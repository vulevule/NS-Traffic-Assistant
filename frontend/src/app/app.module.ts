import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { MenuBarComponent } from './menu-bar/menu-bar.component';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { MainPageComponent } from './pages/main-page/main-page.component';
import { NotFoundPageComponent } from './pages/not-found-page/not-found-page.component';
import { LoggedUserService } from './services/loggedUserService';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { DisplayStationsComponent } from './stations/display-stations/display-stations.component';
import { StationServiceService } from './services/stations/station-service.service';
import {AuthenticationService} from './services/authentication.service';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { FilterByTypePipe } from './stations/display-stations/pipes/filter-by-type.pipe';
import { FilterByNamePipe } from './stations/display-stations/pipes/filter-by-name.pipe';
import { LinesComponent } from './lines/lines/lines.component';
import { LinesSidebarComponent } from './lines/lines-sidebar/lines-sidebar.component';
import { LinesMapComponent } from './lines/lines-map/lines-map.component';
import { LinesDisplayComponent } from './lines/lines-display/lines-display.component';
import { LinesCreateComponent } from './lines/lines-create/lines-create.component';
import { LineService } from './services/lines/line.service';
import { FilterByZonePipe } from './lines/lines-display/pipes/filter-by-zone.pipe';
import { FilterByLinePipe } from './stations/display-stations/pipes/filter-by-line.pipe';
import { FilterByStationPipe } from './lines/lines-display/pipes/filter-by-station.pipe';



const appRoutes: Routes = [
  { path: 'main',
    component: MainPageComponent,
    children: [
      { path: 'displaystations', component: DisplayStationsComponent, outlet: "secondary"},
      { path: 'lines', component: LinesComponent, outlet: "secondary"},
    ] 
  },
  { path: 'login',      component: LoginPageComponent },
  { path: '', redirectTo: '/main', pathMatch: 'full'},
  { path: '**', component: NotFoundPageComponent },
  
];

@NgModule({
  declarations: [
    AppComponent,
    MainPageComponent,
    LoginPageComponent,
    NotFoundPageComponent,
    HeaderComponent,
    MenuBarComponent,
    DisplayStationsComponent,
    FilterByTypePipe,
    FilterByNamePipe,
    LinesComponent,
    LinesSidebarComponent,
    LinesMapComponent,
    LinesDisplayComponent,
    LinesCreateComponent,
    FilterByZonePipe,
    FilterByLinePipe,
    FilterByStationPipe,
  ],
  imports: [
    NgbModule,
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true } // <-- debugging purposes only
    )
  ],
  providers: [
    LoggedUserService,
    AuthenticationService,
    StationServiceService,
    LineService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
