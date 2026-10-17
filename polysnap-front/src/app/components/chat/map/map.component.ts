import {Component, OnInit, ViewChild, ElementRef, AfterViewInit, OnDestroy, Input} from '@angular/core';
import { Map, NavigationControl, Marker } from 'maplibre-gl';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit, AfterViewInit, OnDestroy {
  map: Map | undefined;

  mapOpened:boolean=false;

  @Input() position: any =[];

  @ViewChild('map')
  private mapContainer!: ElementRef<HTMLElement>;

  constructor() { }

  ngOnInit(): void {
  }
  ngOnChanges() {
    if (this.map &&this.position.length>0) {
      this.map.flyTo({
        center: [this.position[0], this.position[1]],
        zoom: 14
      });
      new Marker({color: "#FF0000"})
        .setLngLat([this.position[0],this.position[1]])
        .addTo(this.map);
    }
    else if (this.mapContainer&&this.position.length>0)
    {
      const initialState = { lng: this.position[0], lat: this.position[1], zoom: 14 };
      this.map = new Map({
        container: this.mapContainer.nativeElement,
        style: `https://api.maptiler.com/maps/streets-v2/style.json?key=rKEXiBirdTyaua39KFxt`,
        center: [initialState.lng, initialState.lat],
        zoom: initialState.zoom
      });
      this.map.addControl(new NavigationControl(), 'top-right');
      new Marker({color: "#FF0000"})
        .setLngLat([this.position[0],this.position[1]])
        .addTo(this.map);}
  }

  ngAfterViewInit() {
    if (this.mapContainer&&this.position.length>0){
      const initialState = { lng: this.position[0], lat: this.position[1], zoom: 14 };

      this.map = new Map({
        container: this.mapContainer.nativeElement,
        style: `https://api.maptiler.com/maps/streets-v2/style.json?key=rKEXiBirdTyaua39KFxt`,
        center: [initialState.lng, initialState.lat],
        zoom: initialState.zoom
      });
      this.map.addControl(new NavigationControl(), 'top-right');
      new Marker({color: "#FF0000"})
        .setLngLat([this.position[0],this.position[1]])
        .addTo(this.map);
    }
  }

  ngOnDestroy() {
    this.map?.remove();
  }

  closeMap() {
    this.position = [];
  }

  reload() {
    const initialState = { lng: this.position[0], lat: this.position[1], zoom: 14 };
    this.map = new Map({
      container: this.mapContainer.nativeElement,
      style: `https://api.maptiler.com/maps/streets-v2/style.json?key=rKEXiBirdTyaua39KFxt`,
      center: [initialState.lng, initialState.lat],
      zoom: initialState.zoom
    });
    this.map.addControl(new NavigationControl(), 'top-right');
    new Marker({color: "#FF0000"})
      .setLngLat([this.position[0],this.position[1]])
      .addTo(this.map);
  }
}
