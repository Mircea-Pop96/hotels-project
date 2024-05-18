import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FeedbackService } from '../../services/feedback.service';
import { Feedback } from '../../models/Hotel-Models';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FeedbackDTO } from '../../models/Feedback-Models';

@Component({
  selector: 'app-feedback',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './feedback.component.html',
  styleUrl: './feedback.component.css',
})
export class FeedbackComponent implements OnInit {
  hotelId: number;
  feedbacks: Feedback[] = [];
  newFeedback: FeedbackDTO = {
    user: '',
    services: '',
    cleanliness: '',
    comments: '',
  };

  constructor(
    private route: ActivatedRoute,
    private feedbackService: FeedbackService
  ) {}

  ngOnInit(): void {
    this.hotelId = +this.route.snapshot.paramMap.get('hotelId')!;
    this.loadFeedbacks();
  }

  loadFeedbacks(): void {
    this.feedbackService.getFeedbacksByHotel(this.hotelId).subscribe((data) => {
      this.feedbacks = data;
    });
  }

  addFeedback(): void {
    this.feedbackService
      .addFeedback(this.hotelId, this.newFeedback)
      .subscribe((response) => {
        this.feedbacks.push(response);
        this.newFeedback = {
          user: '',
          services: '',
          cleanliness: '',
          comments: '',
        };
      });
  }
}
