import { Component } from '@angular/core';
import {
  BookingRequest,
  Feedback,
  Hotel,
  Room,
} from '../../models/Hotel-Models';
import { ActivatedRoute, Router } from '@angular/router';
import { HotelService } from '../../services/hotel.service';
import { CommonModule } from '@angular/common';
import { BookingService } from '../../services/booking.service';
import { FormsModule } from '@angular/forms';
import { BookingResponseDTO } from '../../models/Response-Models';
import { FeedbackService } from '../../services/feedback.service';
import { FeedbackDTO } from '../../models/Feedback-Models';

@Component({
  selector: 'app-hotel-detail',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './hotel-detail.component.html',
  styleUrl: './hotel-detail.component.css',
})
export class HotelDetailComponent {
  hotel: Hotel | undefined;
  selectedRooms: Room[] = [];
  feedbacks: Feedback[] = [];
  newFeedback: FeedbackDTO = {
    user: '',
    services: '',
    cleanliness: '',
    comments: '',
  };
  checkInTime: string = '';
  checkOutTime: string = '';
  currentRoomToChange: Room | null = null;

  constructor(
    private route: ActivatedRoute,
    private hotelService: HotelService,
    private bookingService: BookingService,
    private feedbackService: FeedbackService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.hotelService.getHotelById(id).subscribe((data) => {
      this.hotel = data;
      this.loadFeedbacks();
    });
  }

  toggleRoomSelection(room: Room): void {
    const index = this.selectedRooms.indexOf(room);
    if (index === -1) {
      this.selectedRooms.push(room);
    } else {
      this.selectedRooms.splice(index, 1);
    }
  }

  bookRooms(): void {
    if (this.selectedRooms.length === 0) {
      alert('Please select at least one room to book.');
      return;
    }

    const roomIds = this.selectedRooms.map((room) => room.id);
    const bookingRequest: BookingRequest = {
      hotelId: this.hotel!.id,
      roomIds: roomIds,
      checkInTime: this.checkInTime,
      checkOutTime: this.checkOutTime,
    };

    this.bookingService
      .bookRooms(bookingRequest)
      .subscribe((response: BookingResponseDTO) => {
        alert(response.message);

        this.selectedRooms.forEach((room) => {
          const roomToUpdate = this.hotel!.rooms.find((r) => r.id === room.id);
          if (roomToUpdate) {
            roomToUpdate.isAvailable = false;
          }
        });

        this.selectedRooms = this.selectedRooms.filter(
          (room) => !room.isAvailable
        );
      });
  }

  cancelBooking(room: Room): void {
    this.bookingService
      .cancelBooking(room.id)
      .subscribe((response: BookingResponseDTO) => {
        alert(response.message);
        if (response.message === 'Booking cancelled successfully.') {
          room.isAvailable = true;
        }
      });
  }

  initiateChangeBooking(room: Room): void {
    this.currentRoomToChange = room;
  }

  confirmChangeBooking(newRoom: Room): void {
    if (!this.currentRoomToChange) return;

    this.bookingService
      .changeBooking(this.currentRoomToChange.id, newRoom.id)
      .subscribe((response: BookingResponseDTO) => {
        alert(response.message);
        if (response.message === 'Booking changed successfully.') {
          this.currentRoomToChange!.isAvailable = true;
          newRoom.isAvailable = false;

          this.selectedRooms = this.selectedRooms.filter(
            (room) => room.id !== this.currentRoomToChange!.id
          );
          this.selectedRooms.push(newRoom);

          this.currentRoomToChange = null;
        }
      });
  }

  isRoomSelected(room: Room): boolean {
    return this.selectedRooms.includes(room);
  }

  canModifyBooking(checkInTime: string | Date | null): boolean {
    if (!checkInTime) {
      return false;
    }
    const now = new Date();
    const checkInDate =
      typeof checkInTime === 'string' ? new Date(checkInTime) : checkInTime;
    const diffInHours =
      (checkInDate.getTime() - now.getTime()) / 1000 / 60 / 60;
    return diffInHours > 2;
  }

  loadFeedbacks(): void {
    if (this.hotel) {
      this.feedbackService
        .getFeedbacksByHotel(this.hotel.id)
        .subscribe((data) => {
          this.feedbacks = data;
        });
    }
  }

  addFeedback(): void {
    if (this.hotel) {
      this.feedbackService
        .addFeedback(this.hotel.id, this.newFeedback)
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

  goToFeedbackForm() {
    this.router.navigateByUrl(`/hotels/${this.hotel.id}/feedback`);
  }

  canBookRooms(): boolean {
    return (
      this.checkInTime && this.checkOutTime && this.selectedRooms.length > 0
    );
  }
}
