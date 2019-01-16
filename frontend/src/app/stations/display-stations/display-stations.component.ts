import { Component, OnInit } from '@angular/core';
import { StationDTO } from 'src/app/model/StationDTO';
import { StationServiceService } from 'src/app/services/stations/station-service.service';
import { ActivatedRoute } from '@angular/router';

import Map from 'ol/Map';
import View from 'ol/View';
import TileLayer from 'ol/layer/Tile';
import VectorLayer from 'ol/layer/Vector';
import VectorSource from 'ol/source/Vector';
import OSM from 'ol/source/OSM';
import {fromLonLat} from 'ol/proj';
import {transform} from 'ol/proj';
import Style from 'ol/style/Style';
import Icon from 'ol/style/Icon';
import Feature from 'ol/Feature';
import Point from 'ol/geom/Point';
import Stroke from 'ol/style/Stroke';
import LineString from 'ol/geom/LineString';
import Text from 'ol/style/Text';
import Fill from 'ol/style/Fill';
import { Observable } from 'rxjs';
import {debounceTime, distinctUntilChanged, map} from 'rxjs/operators';
import { Station } from 'src/app/model/Station';

@Component({
  selector: 'app-display-stations',
  templateUrl: './display-stations.component.html',
  styleUrls: ['./display-stations.component.css', './general.scss']
})
export class DisplayStationsComponent implements OnInit {

  stations:StationDTO[];
  searchStations:StationDTO[];
  public selectedStation: StationDTO;

  search = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map(term => term.length < 2 ? []
        : this.stations.filter(s => s.name.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 20))
    )

  formatter = (result: StationDTO) => result.name;
  
  displayType = {
    bus: true,
    tram: true,
    metro: true
  }

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
  vectorSource = new VectorSource()


  constructor(private stationService:StationServiceService, private route:ActivatedRoute) { }

  async ngOnInit() {
    this.stations = [];
    this.searchStations = [];
    this.markerOnMap = '';
    this.currentMarker = undefined;
    this.newStation = new StationDTO();
    this.newStation.type = "BUS";
    this.editStation = undefined;
    
    await this.getData();

    // Latitude - Y
    // Longitude - X

    let vm = this
    // create map and set initial layers
    vm.map = new Map({
      target: 'map',
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
    vm.map.on('click', function (args) {   
      // draw marker
      vm.drawMarker(args.coordinate);
      var lonlat = transform(args.coordinate, 'EPSG:3857', 'EPSG:4326');
      vm.markerOnMap = lonlat;
    });
    console.log("CRTAM " + this.stations.length + " STANICA");
    this.drawStations();
  }

  async getData() {
    await this.stationService.getAll().then(data => {
      this.stations = data;
      this.searchStations = data;
    });
  }

  async updateStation(station: StationDTO) {
    station.xCoordinate = this.markerOnMap[0];
    station.yCoordinate = this.markerOnMap[1];

    await this.stationService.updateStation(station).then(data => {
      
      alert(`Station ${data.name} updated!`);
    }, reason => {
      alert("Station with this id does not exist!");
    });

    this.selectedStation = station;
    this.editStation = undefined;
    await this.getData();
    this.drawStations();
  }

  async deleteStation(station: StationDTO) {
    if(confirm(`You are about to delete ${station.name} station.\nAre you sure?`)) {
      await this.stationService.deleteStation(station.id).then(data => {
        
        alert(`Station deleted!`);
      }, reason => {
        alert("Station with this id is not found!");
      });

      this.selectedStation = undefined;
      await this.getData();
      this.drawStations();
    }
  }

  async createStation(station: StationDTO) {
    station.xCoordinate = this.markerOnMap[0];
    station.yCoordinate = this.markerOnMap[1];
    station.lines = [];
    
    await this.stationService.createStation(station).then(data => {
      
      alert(`Station ${data.name} created!`);
    }, reason => {
      alert("Station with this name and type already exists!");
    });

    await this.getData();
    this.drawStations();
    
    this.activeTabId = "displayStationsTab";
  }

  selectStation(station: StationDTO) {
    this.selectedStation = station;
    this.editStation = undefined;
    this.positionOnMap(station);
  }

  positionOnMap(station: StationDTO) {
    this.map.getView().setCenter(transform([station.xCoordinate, station.yCoordinate], 'EPSG:4326', 'EPSG:3857'));
    this.map.getView().setZoom(17);
  }

  drawStations() {
    this.clearMap();

    this.stations.forEach((station) => {
      this.drawStation(station.xCoordinate, station.yCoordinate, station.name, station.type);
    });
  }

  drawMarker(coordinates) {
    if(this.currentMarker) {
      this.vectorSource.removeFeature(this.currentMarker);
    }

    // define style for the marker
    let markerStyle = new Style({
      image: new Icon({
        anchor: [0.5, 1],
        anchorXUnits: 'fraction',
        anchorYUnits: 'fraction',
        opacity: 1,
        src: '../../../assets/images/map-marker.png',
        scale: 0.1
      }),
      zIndex: 5,
      
    });
    // create marker feature and set style
    let marker = new Feature({
      geometry: new Point(coordinates),
      name: 'Marker'
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
        anchorXUnits: 'fraction',
        anchorYUnits: 'fraction',
        opacity: 1,
        src: (stationType === 'BUS' ? '../../../assets/images/BUS-station.png' : (stationType === 'METRO' ? '../../../assets/images/METRO-station.png' : '../../../assets/images/TRAM-station.png')),
        scale: 0.07
      }),
      text: new Text({
        text: stationName,
        stroke: new Stroke({
          color: '#fff' 
        }),
        fill: new Fill({
          color: '#3366cc'
        }),
        font: '15px sans-serif',
        offsetY: -45,
        backgroundFill: new Fill({
          color: 'white'
        })
      })
    });

    let busStation = new Feature({
      geometry: new Point(fromLonLat([lon, lat])),
      name: 'Station'
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

  updateDisplay() {
    
  }

  tabChange() {
    this.newStation = new StationDTO();
    this.newStation.type = "BUS";
    this.markerOnMap = [];
    if(this.currentMarker) {
      this.vectorSource.removeFeature(this.currentMarker);
    }
    this.currentMarker = undefined;
  }

  removeMarkers() {
    this.markerOnMap = [];
    if(this.currentMarker) {
      this.vectorSource.removeFeature(this.currentMarker);
    }
    this.currentMarker = undefined;
  }
}
