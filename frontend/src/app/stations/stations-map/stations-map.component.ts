import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  Output,
  SimpleChanges,
  SimpleChange
} from "@angular/core";
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
import { StationDTO } from "src/app/model/StationDTO";
import { SharedService } from "src/app/services/sharedVars/shared.service";

@Component({
  selector: "app-stations-map",
  templateUrl: "./stations-map.component.html",
  styleUrls: ["./stations-map.component.css", "../general.scss"]
})
export class StationsMapComponent implements OnInit, OnChanges, OnDestroy {
  @Input()
  stations: StationDTO[];
  @Input()
  selectedStation: StationDTO;
  @Input()
  displayType = {
    bus: true,
    tram: false,
    metro: false
  };

  @Output()
  onSelectStation = new EventEmitter<StationDTO>();
  @Output()
  onMapClick = new EventEmitter<any>();

  displayTypeSub: any;
  @Input() displayTypeObs: Observable<any>;
  selectedStationSub: any;
  @Input() selectedStationObs: Observable<any>;

  // Novi Sad coordinates
  latitude = 45.26060794;
  longitude = 19.83221305;
  zoomSize = 16;

  markerOnMap: any;
  currentMarker: any;

  map: any;
  vectorSource = new VectorSource();

  constructor(private sharedService: SharedService) {}

  ngOnChanges(changes: { [propKey: string]: SimpleChange }) {
    if (changes["stations"]) {
      if (changes["stations"].currentValue !== changes["stations"].previousValue) {
        this.drawStations();
      }
    }
    // this.drawStations();
  }

  ngOnInit() {
    this.sharedService.stations.subscribe(
      stations => (this.stations = stations)
    );

    this.selectedStation = undefined;

    if (this.displayTypeObs) {
      this.displayTypeSub = this.displayTypeObs.subscribe(data => {
        this.displayType = data;
        this.drawStations();
      });
    }

    if (this.selectedStationObs) {
      this.selectedStationSub = this.selectedStationObs.subscribe(data => {
        this.selectedStation = data;
        if (this.selectedStation) {
          this.positionOnMap(this.selectedStation);
        }
      });
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
      const f = vm.map.forEachFeatureAtPixel(args.pixel, (ft, layer) => {
        return ft;
      });

      if (f && f.get("name") === "Station") {
        const name = f
          .getStyle()
          .getText()
          .getText();
        const type = f.get("description");

        vm.selectedStation = JSON.parse(
          JSON.stringify(
            vm.stations.find(s => s.name === name && s.type === type)
          )
        );

        vm.onSelectStation.emit(vm.selectedStation);
      } else {
        // draw marker

        const lonlat = transform(args.coordinate, "EPSG:3857", "EPSG:4326");
        vm.drawMarker(args.coordinate, lonlat);

        vm.markerOnMap = lonlat;

        vm.onMapClick.emit(vm.markerOnMap);
      }
    });

    vm.map.on("pointermove", args => {
      let f = vm.map.forEachFeatureAtPixel(args.pixel, (ft, layer) => {
        return ft;
      });

      if (f && f.get("name") === "Station") {
        document.body.style.cursor = "pointer";
      } else {
        document.body.style.cursor = "";
      }
    });

    this.drawStations();
  }

  ngOnDestroy() {
    if (this.displayTypeSub) {
      this.displayTypeSub.unsubscribe();
    }

    if (this.selectedStationSub) {
      this.selectedStationSub.unsubscribe();
    }
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

  drawMarker(coordinates: any, coordAsList: any) {
    this.removeMarkers();

    // define style for the marker
    const markerStyle = new Style({
      image: new Icon({
        anchor: [0.5, 1],
        anchorXUnits: "fraction",
        anchorYUnits: "fraction",
        opacity: 1,
        src: "../../../assets/images/map-marker.png",
        scale: 0.1
      }),
      text: new Text({
        text: coordAsList[0].toFixed(5) + " - " + coordAsList[1].toFixed(5),
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
      }),
      zIndex: 5
    });
    // create marker feature and set style
    const marker = new Feature({
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
    const busStationStyle = new Style({
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

    const busStation = new Feature({
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

  removeMarkers() {
    this.markerOnMap = [];
    if (
      this.currentMarker &&
      this.vectorSource.hasFeature(this.currentMarker)
    ) {
      this.vectorSource.removeFeature(this.currentMarker);
    }
    // this.currentMarker = undefined;
  }
}
