export interface Hotel {
  id: number;
  name: string;
  latitude: number;
  longitude: number;
  rooms: Room[];
  feedbacks: Feedback[];
}

export interface Room {
  id: number;
  roomNumber: number;
  type: string;
  price: number;
  isAvailable: boolean;
  checkInTime?: Date;
  checkOutTime?: Date;
}

export interface Feedback {
  id: number;
  user: string;
  services: string;
  cleanliness: string;
  comments: string;
  feedbackTime: Date;
}

export interface BookingRequest {
  hotelId: number;
  roomIds: number[];
  checkInTime: string;
  checkOutTime: string;
}
