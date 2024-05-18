package com.hotels.project.controller;

import com.hotels.project.entity.Hotel;
import com.hotels.project.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getHotel(@PathVariable Long hotelId) {
        return new ResponseEntity<>(hotelService.getHotel(hotelId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Hotel> addHotel(@RequestBody Hotel hotel) {
        Hotel savedHotel = hotelService.saveHotel(hotel);
        return new ResponseEntity<>(savedHotel, HttpStatus.CREATED);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Hotel>> addHotels(@RequestBody List<Hotel> hotels) {
        List<Hotel> savedHotels = hotelService.saveHotels(hotels);
        return new ResponseEntity<>(savedHotels, HttpStatus.CREATED);
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<Hotel>> findNearbyHotels(
            @RequestParam double userLat,
            @RequestParam double userLon,
            @RequestParam double radiusKm) {
        List<Hotel> nearbyHotels = hotelService.findNearbyHotels(userLat, userLon, radiusKm);
        return new ResponseEntity<>(nearbyHotels, HttpStatus.OK);
    }
}
