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


@Component({
  selector: 'app-display-stations',
  templateUrl: './display-stations.component.html',
  styleUrls: ['./display-stations.component.css', './general.scss']
})
export class DisplayStationsComponent implements OnInit {

  stations:StationDTO[];

  //Novi Sad coordinates
  latitude: number = 45.26060794;
  longitude: number = 19.83221305;
  zoomSize: number = 16;
 
  markersOnMap: any[];

  map: any;
  vectorSource = new VectorSource()

  constructor(private stationService:StationServiceService, private route:ActivatedRoute) { }

  ngOnInit() {
    this.stations = [];
    /* this.stationService.getAll().subscribe(data => {
      this.stations = data;
    }); */

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
      vm.markersOnMap.push(lonlat);
      
    });
  }

  drawMarker(coordinates) {
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
      zIndex: 5
    });
    // create marker feature and set style
    let marker = new Feature({
      geometry: new Point(coordinates),
      name: 'Marker'
    });
    marker.setStyle(markerStyle);
    // add marker to layer
    this.vectorSource.addFeature(marker);
  }

  drawBusStation(lon, lat, stationName) {
    // create station feature and set style
    let busStationStyle = new Style({
      image: new Icon({
        anchor: [0.5, 1],
        anchorXUnits: 'fraction',
        anchorYUnits: 'fraction',
        opacity: 1,
        src: '../../../assets/images/bus-station.png',
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

}
