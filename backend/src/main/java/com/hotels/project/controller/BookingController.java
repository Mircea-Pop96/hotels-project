package com.hotels.project.controller;

import com.hotels.project.dto.BookingRequestDTO;
import com.hotels.project.dto.BookingResponseDTO;
import com.hotels.project.exceptions.ResourceNotFoundException;
import com.hotels.project.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponseDTO> bookRooms(@RequestBody BookingRequestDTO bookingRequest) {
        try {
            String result = bookingService.bookRooms(
                    bookingRequest.getHotelId(),
                    bookingRequest.getRoomIds(),
                    bookingRequest.getCheckInTime(),
                    bookingRequest.getCheckOutTime()
            );
            return new ResponseEntity<>(new BookingResponseDTO(result), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new BookingResponseDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<BookingResponseDTO> cancelBooking(@RequestParam Long roomId) {
        try {
            String result = bookingService.cancelBooking(roomId);
            return new ResponseEntity<>(new BookingResponseDTO(result), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new BookingResponseDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/change")
    public ResponseEntity<BookingResponseDTO> changeBooking(@RequestParam Long currentRoomId, @RequestParam Long newRoomId) {
        try {
            String result = bookingService.changeBooking(currentRoomId, newRoomId);
            return new ResponseEntity<>(new BookingResponseDTO(result), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new BookingResponseDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
