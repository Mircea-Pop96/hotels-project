package com.hotels.project.service;

import com.hotels.project.dto.FeedbackDTO;
import com.hotels.project.entity.Feedback;

import java.util.List;

public interface FeedbackService {

    Feedback addFeedback(Long hotelId, FeedbackDTO feedbackDTO);

    List<Feedback> getFeedbacksByHotel(Long hotelId);
}
