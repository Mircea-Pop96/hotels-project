package com.hotels.project.service;

import com.hotels.project.dto.FeedbackDTO;
import com.hotels.project.entity.Feedback;
import com.hotels.project.entity.Hotel;
import com.hotels.project.exceptions.ResourceNotFoundException;
import com.hotels.project.repository.FeedbackRepository;
import com.hotels.project.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private HotelRepository hotelRepository;

    public Feedback addFeedback(Long hotelId, FeedbackDTO feedbackDTO) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id " + hotelId));

        Feedback feedback = new Feedback();
        feedback.setHotel(hotel);
        feedback.setUser(feedbackDTO.getUser());
        feedback.setServices(feedbackDTO.getServices());
        feedback.setCleanliness(feedbackDTO.getCleanliness());
        feedback.setComments(feedbackDTO.getComments());
        feedback.setFeedbackTime(LocalDateTime.now());

        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getFeedbacksByHotel(Long hotelId) {
        return feedbackRepository.findByHotelId(hotelId);
    }
}
