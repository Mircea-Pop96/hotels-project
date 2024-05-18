import { Injectable } from '@angular/core';
import { HOTEL_URL } from '../constants/services-constants';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Hotel } from '../models/Hotel-Models';

@Injectable({
  providedIn: 'root',
})
export class HotelService {
  private baseUrl = `${HOTEL_URL.apiUrl}/hotels`;

  constructor(private http: HttpClient) {}

  getAllHotels(): Observable<Hotel[]> {
    return this.http.get<Hotel[]>(`${this.baseUrl}`);
  }

  getHotelById(id: number): Observable<Hotel> {
    return this.http.get<Hotel>(`${this.baseUrl}/${id}`);
  }

  addHotel(hotel: Hotel): Observable<Hotel> {
    return this.http.post<Hotel>(this.baseUrl, hotel);
  }

  findNearbyHotels(
    lat: number,
    lon: number,
    radius: number
  ): Observable<Hotel[]> {
    return this.http.get<Hotel[]>(
      `${this.baseUrl}/nearby?userLat=${lat}&userLon=${lon}&radiusKm=${radius}`
    );
  }
}
