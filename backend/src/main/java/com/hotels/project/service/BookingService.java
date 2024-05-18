package com.hotels.project.service;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {

    String bookRooms(Long hotelId, List<Long> roomIds, LocalDateTime checkInTime, LocalDateTime checkOutTime);

    String cancelBooking(Long roomId);

    String changeBooking(Long currentRoomId, Long newRoomId);
}
