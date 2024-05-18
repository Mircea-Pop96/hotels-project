import { Injectable } from '@angular/core';
import { HOTEL_URL } from '../constants/services-constants';
import { HttpClient } from '@angular/common/http';
import { BookingRequest } from '../models/Hotel-Models';
import { Observable } from 'rxjs';
import { BookingResponseDTO } from '../models/Response-Models';

@Injectable({
  providedIn: 'root',
})
export class BookingService {
  private baseUrl = `${HOTEL_URL.apiUrl}/bookings`;

  constructor(private http: HttpClient) {}

  bookRooms(bookingRequest: BookingRequest): Observable<BookingResponseDTO> {
    return this.http.post<BookingResponseDTO>(this.baseUrl, bookingRequest);
  }

  cancelBooking(roomId: number): Observable<BookingResponseDTO> {
    return this.http.post<BookingResponseDTO>(
      `${this.baseUrl}/cancel?roomId=${roomId}`,
      {}
    );
  }

  changeBooking(
    currentRoomId: number,
    newRoomId: number
  ): Observable<BookingResponseDTO> {
    return this.http.post<BookingResponseDTO>(
      `${this.baseUrl}/change?currentRoomId=${currentRoomId}&newRoomId=${newRoomId}`,
      {}
    );
  }
}
