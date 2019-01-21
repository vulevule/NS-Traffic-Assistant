import { Component, OnInit, Input } from '@angular/core';
import { StationDTO } from 'src/app/model/StationDTO';
import { LineDTO } from 'src/app/model/LineDTO';
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

@Component({
  selector: 'app-lines-map',
  templateUrl: './lines-map.component.html',
  styleUrls: ['./lines-map.component.css', "../lines/general.scss"]
})
export class LinesMapComponent implements OnInit {

  @Input()
  stations:StationDTO[];

  @Input()
  lines:LineDTO[];

  // Novi Sad coordinates
  latitude: number = 45.26060794;
  longitude: number = 19.83221305;
  zoomSize: number = 16;

  markerOnMap: any;
  currentMarker: any;

  map: any;
  vectorSource = new VectorSource();

  constructor() { }

  ngOnInit() {


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
    vm.map.on("click", function(args) {
      // draw marker
      vm.drawMarker(args.coordinate);
      var lonlat = transform(args.coordinate, "EPSG:3857", "EPSG:4326");
      vm.markerOnMap = lonlat;
    });
    console.log("CRTAM " + this.lines.length + " LINIJA");
    //this.drawStations();
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

  drawStation(lon, lat, stationName, stationType) {
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
        scale: 0.07
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
        offsetY: -45,
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

  drawStations() {
    this.clearMap();
    this.stations.forEach(station => {
      this.drawStation(
        station.xCoordinate,
        station.yCoordinate,
        station.name,
        station.type
      );
    });
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

  clearMap() {
    // remove features from layer
    this.vectorSource.clear();
  }

  redrawMap() {
    this.clearMap();
    this.drawStations();
  }

}
