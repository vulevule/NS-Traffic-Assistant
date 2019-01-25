import { Component, OnInit, Output, EventEmitter, Input } from "@angular/core";
import { LineDTO } from "src/app/model/LineDTO";
import { StationDTO } from "src/app/model/StationDTO";
import { LineService } from "src/app/services/lines/line.service";
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
import Draw from "ol/interaction/Draw";
import { LocationDTO } from "src/app/model/LocationDTO";
import { StationLineDTO } from "src/app/model/StationLineDTO";
import { SharedService } from "src/app/services/sharedVars/shared.service";
import { Observable } from "rxjs";
import { debounceTime, distinctUntilChanged, map } from "rxjs/operators";

@Component({
  selector: "app-lines-create-edit",
  templateUrl: "./lines-create.component.html",
  styleUrls: ["./lines-create.component.css", "../lines/general.scss"]
})
export class LinesCreateComponent implements OnInit {
  @Input()
  newLine: LineDTO;

  // Novi Sad coordinates
  latitude: number = 45.26060794;
  longitude: number = 19.83221305;
  zoomSize: number = 16;

  markersOnMap: any[];
  creatingRoute: boolean;

  map: any;
  vectorSource = new VectorSource();

  drawingRouteSource: any;

  @Input()
  actionType: String;

  @Input()
  stations: StationDTO[];

  @Input()
  lines: LineDTO[];

  @Output()
  onCreation = new EventEmitter<String>();

  @Output()
  buttonClick = new EventEmitter<String>();

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

  constructor(
    private lineService: LineService,
    private sharedService: SharedService
  ) {}

  ngOnInit() {
    this.sharedService.stations.subscribe(
      stations => (this.stations = stations)
    );

    if (this.actionType === "CREATE" || !this.newLine) {
      this.newLine = new LineDTO();
    } else {
      console.log(this.newLine);
    }

    this.markersOnMap = [];
    this.creatingRoute = false;

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

    // set marker on click event
    vm.map.on("click", args => {
      var f = vm.map.forEachFeatureAtPixel(args.pixel, (ft, layer) => {
        return ft;
      });

      if (f && f.get("name") === "Station") {
        var name = f
          .getStyle()
          .getText()
          .getText();
        var type = vm.newLine.type;
        var station = vm.stations.find(
          (s: StationDTO) => s.name === name && s.type === type
        );

        if (vm.newLine.stations.find(s => s.stationId === station.id)) {
          alert("Same station can not be selected twice!");
        } else {
          var sl = new StationLineDTO();
          sl.stationName = station.name;
          sl.stationId = station.id;
          sl.arrival = 0;
          vm.newLine.stations.push(sl);
        }
      } else {
        var lonlat = transform(args.coordinate, "EPSG:3857", "EPSG:4326");
        vm.markersOnMap.push(lonlat);
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

    this.drawStationsForType(this.newLine.type);
    if (this.actionType === "EDIT") {
      this.drawLine(this.newLine);
    }
  }

  styleFunction(feature: any) {
    var geometry = feature.getGeometry();
    var styles = [
      // linestring
      new Style({
        stroke: new Stroke({
          color: "#ffcc33",
          width: 2
        })
      })
    ];

    geometry.forEachSegment((start: any, end: any) => {
      var dx = end[0] - start[0];
      var dy = end[1] - start[1];
      var rotation = Math.atan2(dy, dx);
      // arrows
      styles.push(
        new Style({
          geometry: new Point(end),
          image: new Icon({
            src: "../../../assets/images/arrow.png",
            anchor: [0.75, 0.5],
            rotateWithView: true,
            rotation: -rotation
          })
        })
      );
    });

    return styles;
  }

  drawLine(line: LineDTO) {
    for (let i = 0; i < line.route.length - 1; i++) {
      let start = fromLonLat([line.route[i].lon, line.route[i].lat]);
      let end = fromLonLat([line.route[i + 1].lon, line.route[i + 1].lat]);
      this.drawLineBetweenLocations(start, end, line.type);
    }
  }

  removeRouteFromMap() {
    this.vectorSource.getFeatures().forEach((feature: any) => {
      let properties = feature.getProperties();
      if (properties.name == "Line") {
        this.vectorSource.removeFeature(feature);
      }
    });
  }

  startCreatingRoute() {
    this.creatingRoute = true;
    this.markersOnMap = [];
    this.newLine.route = [];

    // Remove old route
    this.removeRouteFromMap();

    this.drawingRouteSource = new VectorSource();

    var vector = new VectorLayer({
      source: this.drawingRouteSource,
      style: this.styleFunction
    });

    this.map.addLayer(vector);

    this.map.addInteraction(
      new Draw({
        source: this.drawingRouteSource,
        type: "LineString"
      })
    );
  }

  stopCreatingRoute() {
    this.creatingRoute = false;
    this.newLine.route = [];
    this.map.getInteractions().pop();
    this.map.getLayers().pop;

    for (let i = 0; i < this.markersOnMap.length - 1; i++) {
      let start = fromLonLat(this.markersOnMap[i]);
      let end = fromLonLat(this.markersOnMap[i + 1]);
      this.drawLineBetweenLocations(start, end, this.newLine.type);
    }

    this.markersOnMap.forEach(marker => {
      var loc = new LocationDTO();
      loc.lon = marker[0];
      loc.lat = marker[1];

      this.newLine.route.push(loc);
    });
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
      name: "Station"
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

  drawLineBetweenLocations(startPoint: any, endPoint: any, type: String) {
    let busLineStyle = [
      new Style({
        stroke: new Stroke({
          color: "#1434A0",
          width: 4
        })
      })
    ];
    let tramLineStyle = [
      new Style({
        stroke: new Stroke({
          color: "#0ed145",
          width: 4,
          lineDash: [15, 10]
        })
      })
    ];
    let metroLineStyle = [
      new Style({
        stroke: new Stroke({
          color: "#ec1c24",
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

  createLine(line: LineDTO) {
    if (line.stations.length < 2) {
      alert("Line must containt at least 2 stations!");
      return;
    }

    if (line.route.length < 2) {
      alert("You forgot to draw route on map :(");
      return;
    }

    var max = line.stations[0].arrival;
    for (let i = 1; i < line.stations.length; i++) {
      if (line.stations[i].arrival <= max) {
        alert(
          `Station ${
            line.stations[i].stationName
          } can not have arrival time less than stations before!`
        );
        return;
      } else {
        max = line.stations[i].arrival;
      }
    }

    console.log(line);

    if ((this.actionType === "CREATE")) {
      this.lineService.createLine(line).subscribe(
        data => {
          alert(data);
          this.sharedService.updateAll();
          this.buttonClick.emit("displayLinesTab");
        },
        error => {
          alert(error.error);
        }
      );
    } else if ((this.actionType === "EDIT")) {
      this.lineService.updateLine(line).subscribe(
        data => {
          alert(data);
          this.sharedService.updateAll();
          this.buttonClick.emit("displayLinesTab");
        },
        error => {
          alert(error.error);
        }
      );
    } else {
      alert("Unsupported value of action type: " + this.actionType);
    }
  }

  updateMap() {
    this.clearMap();
    this.drawStationsForType(this.newLine.type);
  }

  clearMap() {
    // remove features from layer
    this.vectorSource.clear();
  }

  clearRoutes() {
    this.newLine.route = [];
    this.removeRouteFromMap();
  }
}
