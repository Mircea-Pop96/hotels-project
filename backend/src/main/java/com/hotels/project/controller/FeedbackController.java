package com.hotels.project.controller;

import com.hotels.project.dto.FeedbackDTO;
import com.hotels.project.entity.Feedback;
import com.hotels.project.exceptions.ResourceNotFoundException;
import com.hotels.project.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/{hotelId}")
    public ResponseEntity<Feedback> addFeedback(@PathVariable Long hotelId, @RequestBody FeedbackDTO feedbackDTO) {
        Feedback feedback = feedbackService.addFeedback(hotelId, feedbackDTO);
        return new ResponseEntity<>(feedback, HttpStatus.CREATED);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<List<Feedback>> getFeedbacksByHotel(@PathVariable Long hotelId) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByHotel(hotelId);
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }
}
