import { Component, OnInit } from '@angular/core';
import { Hotel } from '../../models/Hotel-Models';
import { HotelService } from '../../services/hotel.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-nearby-hotels',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './nearby-hotels.component.html',
  styleUrl: './nearby-hotels.component.css',
})
export class NearbyHotelsComponent implements OnInit {
  hotels: Hotel[] = [];
  radius: number = 5;

  constructor(private hotelService: HotelService) {}

  ngOnInit(): void {}

  getUserLocation(): void {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const lat = position.coords.latitude;
          const lon = position.coords.longitude;
          this.findNearbyHotels(lat, lon);
        },
        (error) => {
          console.error('Error getting location', error);
        }
      );
    } else {
      console.error('Geolocation is not supported by this browser.');
    }
  }

  findNearbyHotels(lat: number, lon: number): void {
    this.hotelService
      .findNearbyHotels(lat, lon, this.radius)
      .subscribe((data) => {
        this.hotels = data;
      });
  }
}
