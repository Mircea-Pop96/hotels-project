package com.hotels.project.service;

import com.hotels.project.entity.Hotel;

import java.util.List;

public interface HotelService {

    List<Hotel> getAllHotels();

    Hotel getHotel(Long hotelId);

    Hotel saveHotel(Hotel hotel);

    List<Hotel> saveHotels(List<Hotel> hotels);

    List<Hotel> findNearbyHotels(double userLat, double userLon, double radiusKm);
}
