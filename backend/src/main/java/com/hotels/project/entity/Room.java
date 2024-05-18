package com.hotels.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hotels.project.enums.RoomType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer roomNumber;

    @Enumerated(EnumType.STRING)
    private RoomType type;

    private Double price;
    private Boolean isAvailable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    @JsonBackReference
    private Hotel hotel;

    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
}