import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";
import { StationDTO } from "src/app/model/StationDTO";
import { LineDTO } from "src/app/model/LineDTO";
import { Observable } from "rxjs";
import { debounceTime, distinctUntilChanged, map } from "rxjs/operators";
import { SharedService } from "src/app/services/sharedVars/shared.service";
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
import LineString from "ol/geom/LineString";
import Overlay from "ol/Overlay";
import { LineService } from 'src/app/services/lines/line.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: "app-lines-display",
  templateUrl: "./lines-display.component.html",
  styleUrls: ["./lines-display.component.css", "../lines/general.scss"]
})
export class LinesDisplayComponent implements OnInit {
  @Input()
  stations: StationDTO[];

  @Input()
  lines: LineDTO[];

  @Output()
  editLineEmitter = new EventEmitter<LineDTO>();

  displayType = {
    bus: true,
    tram: false,
    metro: false
  };

  displayZone = {
    first: true,
    second: false
  };

  editLine: LineDTO;
  selectedLine: LineDTO;
  selectedStation: StationDTO;

  search = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map(term =>
        term.length < 2
          ? []
          : this.lines
              .filter(
                s => s.name.toLowerCase().indexOf(term.toLowerCase()) > -1
              )
              .slice(0, 20)
      )
    );

  formatter = (result: LineDTO) => result.name;

  searchByStation = (text$: Observable<string>) =>
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

  formatterStation = (result: StationDTO) => result.name;

  // Novi Sad coordinates
  latitude: number = 45.26060794;
  longitude: number = 19.83221305;
  zoomSize: number = 16;

  markerOnMap: any;
  currentMarker: any;

  map: any;
  vectorSource = new VectorSource();

  container: any;
  content: any;
  closer: any;
  overlay: any;

  constructor(private sharedService: SharedService, private lineService: LineService) {}

  ngOnInit() {
    this.sharedService.stations.subscribe(
      stations => (this.stations = stations)
    );
    this.sharedService.lines.subscribe(lines => (this.lines = lines));

    this.container = document.getElementById("popup");
    this.content = document.getElementById("popup-content");
    this.closer = document.getElementById("popup-closer");

    this.overlay = new Overlay({
      element: this.container,
      autoPan: true,
      autoPanAnimation: {
        duration: 50
      }
    });

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
      overlays: [vm.overlay],
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

        var station = vm.stations.find(s => s.name === name && s.type === type);

        

        vm.content.innerHTML = vm.generatePopupContent(station);
        vm.overlay.setPosition(args.coordinate);
      } else {
        /* vm.drawMarker(args.coordinate);
        var lonlat = transform(args.coordinate, "EPSG:3857", "EPSG:4326");
        vm.markerOnMap = lonlat; */

        // remove popup
        vm.overlay.setPosition(undefined);
        vm.closer.blur();
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

    vm.closer.onclick = function() {
      vm.overlay.setPosition(undefined);
      vm.closer.blur();
      return false;
    };
  }

  generatePopupContent(station: StationDTO) {
    return '<div><p><img src="../../../assets/images/' + station.type + '-icon.png" style="width: 40px"/>&nbsp; ' + station.name + '</p><span>Lines: ' + station.lines.length + '</span></div>'
  }

  selectLine(line: LineDTO) {
    this.selectedLine = line;
    //this.clearMap();
    this.drawLine(line);
    this.drawStationsForLine(line);
  }

  deleteLine(line: LineDTO) {
    if (
      confirm(`You are about to delete ${line.mark} station.\nAre you sure?`)
    ) {
     this.lineService.deleteLine(line.id).subscribe(
        data => {
          //this.toaster.success(data.toString());
          alert(data.toString());
          this.selectedStation = undefined;
          this.sharedService.updateAll();
        },
        reason => {
          //this.toaster.error(reason.error);
         alert(reason.error);
        }
      );
    }
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

  drawStationsForType(type: String) {
    this.stations.forEach(station => {
      if (station.type === type) {
        this.drawStation(
          station.xCoordinate,
          station.yCoordinate,
          station.name,
          station.type
        );
      }
    });
  }

  drawStationsForLine(line: LineDTO) {
    line.stations.forEach(stationLine => {
      var station = this.stations.find(i => i.id === stationLine.stationId);

      this.drawStation(
        station.xCoordinate,
        station.yCoordinate,
        station.name,
        station.type
      );
    });
  }

  drawLine(line: LineDTO) {
    var colors = ["red", "orange", "yellow", "green", "blue", "purple"];
    var fill = colors[Math.round((colors.length - 1) * Math.random())];
    var fillNew = [
      Math.round(255 * Math.random()),
      Math.round(255 * Math.random()),
      Math.round(255 * Math.random()),
      1
    ];

    for (let i = 0; i < line.route.length - 1; i++) {
      let start = fromLonLat([line.route[i].lon, line.route[i].lat]);
      let end = fromLonLat([line.route[i + 1].lon, line.route[i + 1].lat]);
      this.drawLineBetweenLocations(start, end, line.type, fill);
    }
  }

  drawLineBetweenLocations(
    startPoint: any,
    endPoint: any,
    type: String,
    color: any
  ) {
    var busColor = "#1434A0";
    var tramColor = "#0ed145";
    var metroColor = "#ec1c24";

    let busLineStyle = [
      new Style({
        stroke: new Stroke({
          color: color,
          width: 4
        })
      })
    ];
    let tramLineStyle = [
      new Style({
        stroke: new Stroke({
          color: color,
          width: 4,
          lineDash: [15, 10]
        })
      })
    ];
    let metroLineStyle = [
      new Style({
        stroke: new Stroke({
          color: color,
          width: 4,
          lineDash: [1, 10]
        })
      })
    ];
    // create line feature and set appropriate style
    let line = new Feature({
      geometry: new LineString([startPoint, endPoint]),
      name: "Line"
    });

    line.setStyle(
      type === "BUS"
        ? busLineStyle
        : type === "TRAM"
        ? tramLineStyle
        : metroLineStyle
    );
    // add line to the layer
    this.vectorSource.addFeature(line);
  }

  clearMap() {
    // remove features from layer
    this.vectorSource.clear();
  }

  drawMarker(coordinates: any) {
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
}
