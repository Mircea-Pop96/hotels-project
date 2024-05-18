package com.hotels.project.service;

import com.hotels.project.entity.Hotel;
import com.hotels.project.entity.Room;
import com.hotels.project.exceptions.ResourceNotFoundException;
import com.hotels.project.repository.HotelRepository;
import com.hotels.project.utils.DistanceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService{

    @Autowired
    private HotelRepository hotelRepository;

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Hotel getHotel(Long hotelId) {
        return hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("The hotel with the id " + hotelId + " was not found."));
    }

    public Hotel saveHotel(Hotel hotel) {
        if (hotel.getRooms() != null) {
            for (Room room : hotel.getRooms()) {
                room.setHotel(hotel);
            }
        }
        return hotelRepository.save(hotel);
    }

    public List<Hotel> saveHotels(List<Hotel> hotels) {
        for (Hotel hotel : hotels) {
            if (hotel.getRooms() != null) {
                for (Room room : hotel.getRooms()) {
                    room.setHotel(hotel);
                }
            }
        }
        return hotelRepository.saveAll(hotels);
    }

    public List<Hotel> findNearbyHotels(double userLat, double userLon, double radiusKm) {
        List<Hotel> allHotels = hotelRepository.findAll();
        return allHotels.stream()
                .filter(hotel -> DistanceUtil.calculateDistance(userLat, userLon, hotel.getLatitude(), hotel.getLongitude()) <= radiusKm)
                .collect(Collectors.toList());
    }
}
