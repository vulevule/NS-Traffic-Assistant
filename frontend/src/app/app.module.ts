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
import {ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DisplayStationsComponent } from './stations/display-stations/display-stations.component';
import { StationServiceService } from './services/stations/station-service.service';
import {AuthenticationService} from './services/authentication.service';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { LinesComponent } from './lines/lines/lines.component';
import { LinesDisplayComponent } from './lines/lines-display/lines-display.component';
import { LinesCreateComponent } from './lines/lines-create/lines-create.component';
import { LineService } from './services/lines/line.service';
import { FilterByZonePipe } from './lines/lines-display/pipes/filter-by-zone.pipe';
import { FilterByStationPipe } from './lines/lines-display/pipes/filter-by-station.pipe';
import { TicketComponent } from './ticket/ticket/ticket.component';
import { BuyTicketFormComponent } from './ticket/buy-ticket-form/buy-ticket-form.component';
import { DisplayTicketComponent } from './ticket/display-ticket/display-ticket.component';
import { SearchFormComponent } from './ticket/search-form/search-form.component';
import { FilterByTrafficTypePipe } from './ticket/pipes/filter-by-traffic-type.pipe';
import { TokenInterceptorService } from './services/token-interceptor.service';

import { PricelistComponent } from './pricelist/pricelist/pricelist.component';
import { DisplayPriceListComponent } from './pricelist/display-price-list/display-price-list.component';
import { CreateNewPricelistComponent } from './pricelist/create-new-pricelist/create-new-pricelist.component';
import { UseCheckTicketComponent } from './ticket/use-check-ticket/use-check-ticket.component';
import { ReportComponent } from './report/report/report.component';
import { DisplayReportComponent } from './report/display-report/display-report.component';
import { FilterItemByTrafficTypePipe } from './pricelist/display-price-list/pipes/filter-item-by-traffic-type.pipe';
import { FilterItemByZonePipe } from './pricelist/display-price-list/pipes/filter-item-by-zone.pipe';
import { FilterItemByTicketTimePipe } from './pricelist/display-price-list/pipes/filter-item-by-ticket-time.pipe';
import { StationsComponent } from './stations/stations/stations.component';
import { StationsCreateComponent } from './stations/stations-create/stations-create.component';
import { StationsDisplayComponent } from './stations/stations-display/stations-display.component';
import { StationsMapComponent } from './stations/stations-map/stations-map.component';
import { FilterByTypePipe } from './stations/pipes/filter-by-type.pipe';
import { FilterByNamePipe } from './stations/pipes/filter-by-name.pipe';
import { FilterByLinePipe } from './stations/pipes/filter-by-line.pipe';



const appRoutes: Routes = [
  { path: 'main',
    component: MainPageComponent,
    children: [
      { path: 'stations', component: StationsComponent},
      { path: 'lines', component: LinesComponent},
      { path: 'login', component: LoginPageComponent},
      { path : 'ticket' , component : TicketComponent},
      { path : 'pricelist', component : PricelistComponent},
      { path : 'report', component : ReportComponent}
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
    LinesDisplayComponent,
    LinesCreateComponent,
    FilterByZonePipe,
    FilterByLinePipe,
    FilterByStationPipe,
    TicketComponent,
    BuyTicketFormComponent,
    DisplayTicketComponent,
    SearchFormComponent,
    FilterByTrafficTypePipe,
    PricelistComponent,
    DisplayPriceListComponent,
    CreateNewPricelistComponent,
    UseCheckTicketComponent,
    ReportComponent,
    DisplayReportComponent,
    FilterItemByTrafficTypePipe,
    FilterItemByZonePipe,
    FilterItemByTicketTimePipe,
    StationsComponent,
    StationsCreateComponent,
    StationsDisplayComponent,
    StationsMapComponent,
  ],
  imports: [
    NgbModule,
    BrowserModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    
    /* ToastrModule.forRoot({
      timeOut: 3000,
      positionClass: 'toast-top-center'
    }), */

    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true } // <-- debugging purposes only
    )
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptorService, multi: true },
    LoggedUserService,
    AuthenticationService,
    StationServiceService,
    LineService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
