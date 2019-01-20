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
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FilterByTypePipe } from './stations/display-stations/pipes/filter-by-type.pipe';
import { FilterByNamePipe } from './stations/display-stations/pipes/filter-by-name.pipe';
import { FilterByAddressPipe } from './stations/display-stations/pipes/filter-by-address.pipe';
import { TicketComponent } from './ticket/ticket/ticket.component';
import { BuyTicketFormComponent } from './ticket/buy-ticket-form/buy-ticket-form.component';
import { DisplayTicketComponent } from './ticket/display-ticket/display-ticket.component';
import { SearchFormComponent } from './ticket/search-form/search-form.component';
import { FilterByTrafficTypePipe } from './ticket/pipes/filter-by-traffic-type.pipe';
import { TokenInterceptorService } from './services/token-interceptor.service';



const appRoutes: Routes = [
  { path: 'main',
    component: MainPageComponent,
    children: [
      { path: 'displaystations', component: DisplayStationsComponent, outlet: "secondary"},
      { path: 'login', component: LoginPageComponent, outlet: "primary"},
      { path : 'ticket' , component : TicketComponent, outlet:"secondary"}
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
    FilterByAddressPipe,
    TicketComponent,
    BuyTicketFormComponent,
    DisplayTicketComponent,
    SearchFormComponent,
    FilterByTrafficTypePipe,
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
    {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptorService, multi: true },,
    LoggedUserService,
    AuthenticationService,
    StationServiceService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
