import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { RouterModule, Routes } from "@angular/router";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { AppComponent } from "./app.component";
import { HeaderComponent } from "./header/header.component";
import { LinesCreateComponent } from "./lines/lines-create/lines-create.component";
import { LinesDisplayComponent } from "./lines/lines-display/lines-display.component";
import { FilterByStationPipe } from "./lines/lines-display/pipes/filter-by-station.pipe";
import { FilterByZonePipe } from "./lines/lines-display/pipes/filter-by-zone.pipe";
import { LinesComponent } from "./lines/lines/lines.component";
import { MenuBarComponent } from "./menu-bar/menu-bar.component";
import { LoginPageComponent } from "./pages/login-page/login-page.component";
import { MainPageComponent } from "./pages/main-page/main-page.component";
import { NotFoundPageComponent } from "./pages/not-found-page/not-found-page.component";
import { MatchingPasswordDirective } from "./pages/register-page/matching-password.directive";
import { RegisterPageComponent } from "./pages/register-page/register-page.component";
import { CreateNewPricelistComponent } from "./pricelist/create-new-pricelist/create-new-pricelist.component";
import { DisplayPriceListComponent } from "./pricelist/display-price-list/display-price-list.component";
import { FilterItemByTicketTimePipe } from "./pricelist/display-price-list/pipes/filter-item-by-ticket-time.pipe";
import { FilterItemByTrafficTypePipe } from "./pricelist/display-price-list/pipes/filter-item-by-traffic-type.pipe";
import { FilterItemByZonePipe } from "./pricelist/display-price-list/pipes/filter-item-by-zone.pipe";
import { PricelistComponent } from "./pricelist/pricelist/pricelist.component";
import { DisplayReportComponent } from "./report/display-report/display-report.component";
import { ReportComponent } from "./report/report/report.component";
import { AuthenticationService } from "./services/authentication.service";
import { LineService } from "./services/lines/line.service";
import { LoggedUserService } from "./services/loggedUserService";
import { PriceListServiceService } from "./services/pricelist/price-list-service.service";
import { StationServiceService } from "./services/stations/station-service.service";
import { TicketServiceService } from "./services/ticket/ticket-service.service";
import { TimetableService } from "./services/timetable/timetable.service";
import { TokenInterceptorService } from "./services/token-interceptor.service";
import { DisplayStationsComponent } from "./stations/display-stations/display-stations.component";
import { FilterByLinePipe } from "./stations/pipes/filter-by-line.pipe";
import { FilterByNamePipe } from "./stations/pipes/filter-by-name.pipe";
import { FilterByTypePipe } from "./stations/pipes/filter-by-type.pipe";
import { StationsCreateComponent } from "./stations/stations-create/stations-create.component";
import { StationsDisplayComponent } from "./stations/stations-display/stations-display.component";
import { StationsMapComponent } from "./stations/stations-map/stations-map.component";
import { StationsComponent } from "./stations/stations/stations.component";
import { BuyTicketFormComponent } from "./ticket/buy-ticket-form/buy-ticket-form.component";
import { DisplayTicketComponent } from "./ticket/display-ticket/display-ticket.component";
import { FilterByTrafficTypePipe } from "./ticket/pipes/filter-by-traffic-type.pipe";
import { SearchFormComponent } from "./ticket/search-form/search-form.component";
import { TicketComponent } from "./ticket/ticket/ticket.component";
import { UseCheckTicketComponent } from "./ticket/use-check-ticket/use-check-ticket.component";
import { DisplayTimetableComponent } from "./timetable/display-timetable/display-timetable.component";
import { EditTimetableComponent } from "./timetable/edit-timetable/edit-timetable.component";
import { SearchTimetableComponent } from "./timetable/search-timetable/search-timetable.component";
import { TimetableComponent } from "./timetable/timetable/timetable.component";

const appRoutes: Routes = [
  {
    path: "main",
    component: MainPageComponent,
    children: [
      { path: "stations", component: StationsComponent },
      { path: "lines", component: LinesComponent },
      { path: "login", component: LoginPageComponent },
      { path: "ticket", component: TicketComponent },
      { path: "pricelist", component: PricelistComponent },
      { path: "report", component: ReportComponent },
      { path: "timetable", component: TimetableComponent }
    ]
  },

  { path: "login", component: LoginPageComponent },
  { path: "", redirectTo: "/main", pathMatch: "full" },
  { path: "**", component: NotFoundPageComponent }
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

    RegisterPageComponent,
    MatchingPasswordDirective,

    TimetableComponent,
    EditTimetableComponent,
    DisplayTimetableComponent,
    SearchTimetableComponent
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
    BrowserAnimationsModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    },

    LoggedUserService,
    AuthenticationService,
    StationServiceService,

    LineService,

    TicketServiceService,
    PriceListServiceService,
    TimetableService
  ],

  bootstrap: [AppComponent],
  entryComponents: [UseCheckTicketComponent, RegisterPageComponent]
})
export class AppModule {}
