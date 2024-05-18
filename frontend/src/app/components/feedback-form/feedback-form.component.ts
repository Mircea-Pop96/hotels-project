import { Component } from '@angular/core';
import { FeedbackDTO } from '../../models/Feedback-Models';
import { ActivatedRoute, Router } from '@angular/router';
import { FeedbackService } from '../../services/feedback.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-feedback-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './feedback-form.component.html',
  styleUrl: './feedback-form.component.css',
})
export class FeedbackFormComponent {
  hotelId: number;
  newFeedback: FeedbackDTO = {
    user: '',
    services: '',
    cleanliness: '',
    comments: ''
  };

  constructor(
    private route: ActivatedRoute,
    private feedbackService: FeedbackService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.hotelId = +this.route.snapshot.paramMap.get('hotelId')!;
  }

  addFeedback(feedbackForm: any): void {
    if (feedbackForm.form.valid) {
      this.feedbackService.addFeedback(this.hotelId, this.newFeedback).subscribe(() => {
        this.router.navigate([`/hotels/${this.hotelId}`]);
      });
    }
  }
}
