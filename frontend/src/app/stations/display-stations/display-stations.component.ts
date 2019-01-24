import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import Feature from "ol/Feature";
import Point from "ol/geom/Point";
import TileLayer from "ol/layer/Tile";
import VectorLayer from "ol/layer/Vector";
import Map from "ol/Map";
import { fromLonLat, transform } from "ol/proj";
import OSM from "ol/source/OSM";
import VectorSource from "ol/source/Vector";
import Fill from "ol/style/Fill";
import Icon from "ol/style/Icon";
import Stroke from "ol/style/Stroke";
import Style from "ol/style/Style";
import Text from "ol/style/Text";
import View from "ol/View";
import { Observable } from "rxjs";
import { debounceTime, distinctUntilChanged, map } from "rxjs/operators";
import { LineDTO } from "src/app/model/LineDTO";
import { StationDTO } from "src/app/model/StationDTO";
import { SharedService } from "src/app/services/sharedVars/shared.service";
import { StationServiceService } from "src/app/services/stations/station-service.service";
import { LineService } from "src/app/services/lines/line.service";

@Component({
  selector: "app-display-stations",
  templateUrl: "./display-stations.component.html",
  styleUrls: ["./display-stations.component.css", "./general.scss"]
})
export class DisplayStationsComponent implements OnInit {
  stations: StationDTO[];
  lines: LineDTO[];
  searchStations: StationDTO[];
  selectedStation: StationDTO;
  selectedLine: LineDTO;

  search = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map(term =>
        term.length < 2
          ? []
          : this.stations
              .filter(
                s =>
                  s.name.toLowerCase().indexOf(term.toLowerCase()) > -1 &&
                  this.displayType[s.type.toLowerCase()]
              )
              .slice(0, 20)
      )
    );

  formatter = (result: StationDTO) => result.name;

  searchByLine = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map(term =>
        term.length < 2
          ? []
          : this.lines
              .filter(
                s =>
                  s.name.toLowerCase().indexOf(term.toLowerCase()) > -1 &&
                  this.displayType[s.type.toLowerCase()]
              )
              .slice(0, 20)
      )
    );

  formatterLine = (result: LineDTO) => result.name;

  displayType = {
    bus: true,
    tram: false,
    metro: false
  };

  newStation: StationDTO;
  editStation: StationDTO;

  activeTabId: String;

  // Novi Sad coordinates
  latitude: number = 45.26060794;
  longitude: number = 19.83221305;
  zoomSize: number = 16;

  markerOnMap: any;
  currentMarker: any;

  map: any;
  vectorSource = new VectorSource();

  constructor(
    private stationService: StationServiceService,
    private lineService: LineService,
    private route: ActivatedRoute,
    private sharedService: SharedService
  ) {}

  async ngOnInit() {
    this.stations = [];
    this.searchStations = [];
    this.markerOnMap = "";
    this.currentMarker = undefined;
    this.newStation = new StationDTO();
    this.newStation.type = "BUS";
    this.editStation = undefined;

    this.sharedService.stations.subscribe(
      stations => (this.stations = stations)
    );
    this.sharedService.lines.subscribe(lines => (this.lines = lines));

    if (this.stations.length === 0 && this.stations.length === 0) {
      await this.getData();
    }

    // Latitude - Y
    // Longitude - X

    let vm = this;
    // create map and set initial layers
    vm.map = new Map({
      target: "map",
      layers: [
        new TileLayer({
          source: new OSM()
        }),
        new VectorLayer({
          source: this.vectorSource
        })
      ],
      view: new View({
        center: fromLonLat([this.longitude, this.latitude]),
        zoom: this.zoomSize
      })
    });

    vm.map.on("click", function(args) {
      var f = vm.map.forEachFeatureAtPixel(args.pixel, (ft, layer) => {
        return ft;
      });

      if (f && f.get("name") === "Station") {
        var name = f
          .getStyle()
          .getText()
          .getText();
        var type = f.get("description");

        vm.selectedStation = vm.stations.find(
          s => s.name === name && s.type === type
        );
      } else {
        // draw marker
        vm.drawMarker(args.coordinate);
        var lonlat = transform(args.coordinate, "EPSG:3857", "EPSG:4326");
        vm.markerOnMap = lonlat;
      }
    });

    vm.map.on("pointermove", args => {
      var f = vm.map.forEachFeatureAtPixel(args.pixel, (ft, layer) => {
        return ft;
      });

      if (f && f.get("name") === "Station") {
        document.body.style.cursor = "pointer";
      } else {
        document.body.style.cursor = "";
      }
    });

    console.log("CRTAM " + this.stations.length + " STANICA");

    this.drawStations();
  }

  async getData() {
    await this.stationService.getAll().then(data => {
      this.stations = data;
      this.searchStations = data;
    });

    await this.lineService.getAll().subscribe(data => {
      this.lines = data;
    });
  }

  async updateStation(station: StationDTO) {
    station.xCoordinate = this.markerOnMap[0];
    station.yCoordinate = this.markerOnMap[1];

    await this.stationService.updateStation(station).then(
      data => {
        alert(`Station ${data.name} updated!`);
      },
      reason => {
        alert("Station with this id does not exist!");
      }
    );

    this.selectedStation = station;
    this.editStation = undefined;
    await this.getData();
    this.sharedService.updateAll();
    this.drawStations();
  }

  async deleteStation(station: StationDTO) {
    if (
      confirm(`You are about to delete ${station.name} station.\nAre you sure?`)
    ) {
      await this.stationService.deleteStation(station.id).then(
        data => {
          alert(`Station deleted!`);
        },
        reason => {
          alert("Station with this id is not found!");
        }
      );

      this.selectedStation = undefined;
      await this.getData();
      this.sharedService.updateAll();
      this.drawStations();
    }
  }

  async createStation(station: StationDTO) {
    station.xCoordinate = this.markerOnMap[0];
    station.yCoordinate = this.markerOnMap[1];
    station.lines = [];

    await this.stationService.createStation(station).then(
      data => {
        alert(`Station ${data.name} created!`);
      },
      reason => {
        alert("Station with this name and type already exists!");
      }
    );

    await this.getData();
    this.sharedService.updateAll();
    this.drawStations();

    this.activeTabId = "displayStationsTab";
  }

  selectStation(station: StationDTO) {
    this.selectedStation = station;
    this.editStation = undefined;
    this.positionOnMap(station);
  }

  positionOnMap(station: StationDTO) {
    this.map
      .getView()
      .setCenter(
        transform(
          [station.xCoordinate, station.yCoordinate],
          "EPSG:4326",
          "EPSG:3857"
        )
      );
    this.map.getView().setZoom(17);
  }

  drawStations() {
    this.clearMap();
    this.stations.forEach(station => {
      if (this.displayType[station.type.toLowerCase()]) {
        this.drawStation(
          station.xCoordinate,
          station.yCoordinate,
          station.name,
          station.type
        );
      }
    });
  }

  drawMarker(coordinates) {
    if (this.currentMarker) {
      this.vectorSource.removeFeature(this.currentMarker);
    }

    // define style for the marker
    let markerStyle = new Style({
      image: new Icon({
        anchor: [0.5, 1],
        anchorXUnits: "fraction",
        anchorYUnits: "fraction",
        opacity: 1,
        src: "../../../assets/images/map-marker.png",
        scale: 0.1
      }),
      zIndex: 5
    });
    // create marker feature and set style
    let marker = new Feature({
      geometry: new Point(coordinates),
      name: "Marker"
    });
    marker.setStyle(markerStyle);
    // add marker to layer
    this.currentMarker = marker;

    this.vectorSource.addFeature(marker);
  }

  drawStation(
    lon: Number,
    lat: Number,
    stationName: String,
    stationType: String
  ) {
    // create station feature and set style
    let busStationStyle = new Style({
      image: new Icon({
        anchor: [0.5, 1],
        anchorXUnits: "fraction",
        anchorYUnits: "fraction",
        opacity: 1,
        src:
          stationType === "BUS"
            ? "../../../assets/images/BUS-station.png"
            : stationType === "METRO"
            ? "../../../assets/images/METRO-station.png"
            : "../../../assets/images/TRAM-station.png",
        scale: 0.09
      }),
      text: new Text({
        text: stationName,
        stroke: new Stroke({
          color: "#fff"
        }),
        fill: new Fill({
          color: "#3366cc"
        }),
        font: "15px sans-serif",
        offsetY: -62,
        backgroundFill: new Fill({
          color: "white"
        })
      })
    });

    let busStation = new Feature({
      geometry: new Point(fromLonLat([lon, lat])),
      name: "Station",
      description: stationType
    });

    busStation.setStyle(busStationStyle);
    // add bus station to the layer
    this.vectorSource.addFeature(busStation);
  }

  clearMap() {
    // remove features from layer
    this.vectorSource.clear();
  }

  redrawMap() {
    this.clearMap();
    this.drawStations();
  }

  updateDisplay() {}

  tabChange() {
    this.newStation = new StationDTO();
    this.newStation.type = "BUS";
    this.removeMarkers();
  }

  removeMarkers() {
    this.markerOnMap = [];
    if (this.currentMarker) {
      this.vectorSource.removeFeature(this.currentMarker);
    }
    this.currentMarker = undefined;
  }
}
