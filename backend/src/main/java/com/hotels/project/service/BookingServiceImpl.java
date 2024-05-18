package com.hotels.project.service;

import com.hotels.project.entity.Room;
import com.hotels.project.exceptions.ResourceNotFoundException;
import com.hotels.project.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService{

    @Autowired
    private RoomRepository roomRepository;

    public String bookRooms(Long hotelId, List<Long> roomIds, LocalDateTime checkInTime, LocalDateTime checkOutTime) {
        for (Long roomId : roomIds) {
            Optional<Room> roomOptional = roomRepository.findById(roomId);
            if (roomOptional.isPresent()) {
                Room room = roomOptional.get();
                if (room.getHotel().getId().equals(hotelId) && room.getIsAvailable()) {
                    room.setIsAvailable(false);
                    room.setCheckInTime(checkInTime);
                    room.setCheckOutTime(checkOutTime);
                    roomRepository.save(room);
                } else {
                    throw new ResourceNotFoundException("Room " + roomId + " cannot be booked.");
                }
            } else {
                throw new ResourceNotFoundException("Room " + roomId + " not found.");
            }
        }
        return "Rooms booked successfully.";
    }

    public String cancelBooking(Long roomId) {
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();
            LocalDateTime now = LocalDateTime.now();
            if (room.getCheckInTime() != null && room.getCheckInTime().isAfter(now.plusHours(2))) {
                room.setIsAvailable(true);
                room.setCheckInTime(null);
                room.setCheckOutTime(null);
                roomRepository.save(room);
                return "Booking cancelled successfully.";
            } else {
                return "Cancellation not allowed within 2 hours of check-in.";
            }
        } else {
            throw new ResourceNotFoundException("Room not found with id " + roomId);
        }
    }

    public String changeBooking(Long currentRoomId, Long newRoomId) {
        Optional<Room> currentRoomOptional = roomRepository.findById(currentRoomId);
        Optional<Room> newRoomOptional = roomRepository.findById(newRoomId);

        if (currentRoomOptional.isPresent() && newRoomOptional.isPresent()) {
            Room currentRoom = currentRoomOptional.get();
            Room newRoom = newRoomOptional.get();

            LocalDateTime now = LocalDateTime.now();
            if (currentRoom.getCheckInTime() != null && currentRoom.getCheckInTime().isAfter(now.plusHours(2)) && newRoom.getIsAvailable()) {
                newRoom.setIsAvailable(false);
                newRoom.setCheckInTime(currentRoom.getCheckInTime());
                newRoom.setCheckOutTime(currentRoom.getCheckOutTime());

                currentRoom.setIsAvailable(true);
                currentRoom.setCheckInTime(null);
                currentRoom.setCheckOutTime(null);

                roomRepository.save(currentRoom);
                roomRepository.save(newRoom);

                return "Booking changed successfully.";
            } else {
                return "Room change not allowed within 2 hours of check-in or new room not available.";
            }
        } else {
            throw new ResourceNotFoundException("One or both rooms not found.");
        }
    }
}
