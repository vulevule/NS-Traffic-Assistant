import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
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
import { LinesComponent } from './lines/lines/lines.component';
import { LinesDisplayComponent } from './lines/lines-display/lines-display.component';
import { LinesCreateComponent } from './lines/lines-create/lines-create.component';
import { LineService } from './services/lines/line.service';
import { FilterByZonePipe } from './lines/lines-display/pipes/filter-by-zone.pipe';
import { FilterByLinePipe } from './stations/display-stations/pipes/filter-by-line.pipe';
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
import { RegisterPageComponent } from './pages/register-page/register-page.component';
import { MatchingPasswordDirective } from './pages/register-page/matching-password.directive';


import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import { timeout } from 'q';
import { TicketServiceService } from './services/ticket/ticket-service.service';
import { PriceListServiceService } from './services/pricelist/price-list-service.service';
import { TimetableComponent } from './timetable/timetable/timetable.component';
import { EditTimetableComponent } from './timetable/edit-timetable/edit-timetable.component';
import { DisplayTimetableComponent } from './timetable/display-timetable/display-timetable.component';
import { TimetableService } from './services/timetable/timetable.service';
import { SearchTimetableComponent } from './timetable/search-timetable/search-timetable.component';
import { DialogComponent } from './user/dialog/dialog.component';
import { ValidationDialogComponent } from './user/validation-dialog/validation-dialog.component';
import { EditProfileComponent } from './user/edit-profile/edit-profile.component';
import { ValidateUserComponent } from './user/validate-user/validate-user.component';


const appRoutes: Routes = [
  { path: 'main',
    component: MainPageComponent,
    children: [
      { path: 'displaystations', component: DisplayStationsComponent, outlet: "secondary"},
      { path: 'lines', component: LinesComponent, outlet: "secondary"},
      { path: 'login', component: LoginPageComponent, outlet: "primary"},
      { path : 'ticket' , component : TicketComponent, outlet:"secondary"},
      { path : 'pricelist', component : PricelistComponent, outlet : "secondary"},
      { path : 'report', component : ReportComponent, outlet : "secondary"}, 
      { path : 'timetable', component : TimetableComponent, outlet : "secondary"}
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

    RegisterPageComponent,
    MatchingPasswordDirective,

    TimetableComponent,
    EditTimetableComponent,
    DisplayTimetableComponent,
    SearchTimetableComponent,
    DialogComponent,
    ValidationDialogComponent,
    EditProfileComponent,
    ValidateUserComponent,

  ],
  imports: [
    NgbModule,
    BrowserModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true } // <-- debugging purposes only
    ), 
    BrowserAnimationsModule,
  
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptorService, multi: true },
    
    LoggedUserService,
    AuthenticationService,
    StationServiceService,

    LineService,
    
    TicketServiceService, 
    PriceListServiceService, 
    TimetableService

  ],


  bootstrap: [AppComponent],
  entryComponents: [ UseCheckTicketComponent,RegisterPageComponent,DialogComponent,ValidationDialogComponent,EditProfileComponent ]

})
export class AppModule { }
