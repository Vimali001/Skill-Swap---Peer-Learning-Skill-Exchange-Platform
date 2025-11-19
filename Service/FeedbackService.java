package com.skillswap.service;

import com.skillswap.model.Feedback;
import com.skillswap.model.User;
import com.skillswap.repository.FeedbackRepository;
import com.skillswap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    public Feedback addFeedback(Long fromUserId, Long toUserId, Integer rating, String comments) {
        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new RuntimeException("From user not found"));
        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new RuntimeException("To user not found"));

        Feedback feedback = new Feedback(fromUser, toUser, rating, comments);
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getUserFeedback(Long userId) {
        return feedbackRepository.findByToUserUserId(userId);
    }

    public double getAverageRating(Long userId) {
        List<Feedback> feedbacks = feedbackRepository.findByToUserUserId(userId);
        if (feedbacks.isEmpty()) {
            return 0.0;
        }
        return feedbacks.stream()
                .mapToInt(Feedback::getRating)
                .average()
                .orElse(0.0);
    }
}

