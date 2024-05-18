export interface FeedbackDTO {
  user: string;
  services: string;
  cleanliness: string;
  comments: string;
}

export interface Feedback {
  id: number;
  user: string;
  services: string;
  cleanliness: string;
  comments: string;
  feedbackTime: Date;
}
