package com.hotels.project.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingRequestDTO {
    private Long hotelId;
    private List<Long> roomIds;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
}
