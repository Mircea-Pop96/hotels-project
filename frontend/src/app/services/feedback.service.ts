import { Injectable } from '@angular/core';
import { HOTEL_URL } from '../constants/services-constants';
import { HttpClient } from '@angular/common/http';
import { Feedback } from '../models/Hotel-Models';
import { Observable } from 'rxjs';
import { FeedbackDTO } from '../models/Feedback-Models';

@Injectable({
  providedIn: 'root',
})
export class FeedbackService {
  private baseUrl = `${HOTEL_URL.apiUrl}/feedbacks`;

  constructor(private http: HttpClient) {}

  addFeedback(hotelId: number, feedbackDTO: FeedbackDTO): Observable<Feedback> {
    return this.http.post<Feedback>(`${this.baseUrl}/${hotelId}`, feedbackDTO);
  }

  getFeedbacksByHotel(hotelId: number): Observable<Feedback[]> {
    return this.http.get<Feedback[]>(`${this.baseUrl}/${hotelId}`);
  }
}
