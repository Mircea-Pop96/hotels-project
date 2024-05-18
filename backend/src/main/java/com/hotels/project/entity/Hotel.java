package com.hotels.project.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double latitude;
    private Double longitude;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Room> rooms;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "hotel-feedback")
    private List<Feedback> feedbacks;

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
        if (rooms != null) {
            for (Room room : rooms) {
                room.setHotel(this);
            }
        }
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
        if (feedbacks != null) {
            for (Feedback feedback : feedbacks) {
                feedback.setHotel(this);
            }
        }
    }
}
