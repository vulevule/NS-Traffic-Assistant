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
import { FilterByTypePipe } from './stations/pipes/filter-by-type.pipe';
import { FilterByNamePipe } from './stations/pipes/filter-by-name.pipe';
import { LinesComponent } from './lines/lines/lines.component';
import { LinesDisplayComponent } from './lines/lines-display/lines-display.component';
import { LinesCreateComponent } from './lines/lines-create/lines-create.component';
import { LineService } from './services/lines/line.service';
import { FilterByZonePipe } from './lines/lines-display/pipes/filter-by-zone.pipe';
import { FilterByLinePipe } from './stations/pipes/filter-by-line.pipe';
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
import { SharedService } from './services/sharedVars/shared.service';
import { FilterTicketByZonePipe } from './ticket/pipes/filter-ticket-by-zone.pipe';
import { FilterTicketByTicketTimePipe } from './ticket/pipes/filter-ticket-by-ticket-time.pipe';
import { TimetableComponent } from './timetable/timetable/timetable.component';
import { DisplayTimetableComponent } from './timetable/display-timetable/display-timetable.component';
import { SearchTimetableComponent } from './timetable/search-timetable/search-timetable.component';
import { TicketServiceService } from './services/ticket/ticket-service.service';
import { PriceListServiceService } from './services/pricelist/price-list-service.service';
import { TimetableService } from './services/timetable/timetable.service';
import { NgMultiSelectDropDownModule} from 'ng-multiselect-dropdown';
import { CreateTimetableComponent } from './timetable/create-timetable/create-timetable.component';
import { FilterTicketBySnoPipe } from './ticket/pipes/filter-ticket-by-sno.pipe';



const appRoutes: Routes = [
  { path: 'main',
    component: MainPageComponent,
    children: [
      { path: 'stations', component: StationsComponent},
      { path: 'lines', component: LinesComponent},
      { path: 'login', component: LoginPageComponent},
      { path : 'ticket' , component : TicketComponent},
      { path : 'pricelist', component : PricelistComponent},
      { path : 'report', component : ReportComponent}, 
      { path : 'timetable', component : TimetableComponent}
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
    FilterTicketByZonePipe,
    FilterTicketByTicketTimePipe,
    TimetableComponent,
    DisplayTimetableComponent,
    SearchTimetableComponent,
    CreateTimetableComponent,
    FilterTicketBySnoPipe,
  ],
  imports: [
    NgbModule,
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true } // <-- debugging purposes only
    ),
    NgMultiSelectDropDownModule.forRoot()
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptorService, multi: true },
    LoggedUserService,
    AuthenticationService,
    StationServiceService,
    SharedService,
    LineService, 
    TicketServiceService, 
    PriceListServiceService, 
    TimetableService
  ],
  bootstrap: [AppComponent],
  entryComponents: [ UseCheckTicketComponent, ]
})
export class AppModule { }
