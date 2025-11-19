package com.skillswap.controller;

import com.skillswap.model.Feedback;
import com.skillswap.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "*")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<?> addFeedback(
            @RequestBody Map<String, Object> request,
            HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Please login first"
            ));
        }
        try {
            Long toUserId = Long.valueOf(request.get("toUserId").toString());
            Integer rating = Integer.valueOf(request.get("rating").toString());
            String comments = (String) request.get("comments");
            
            Feedback feedback = feedbackService.addFeedback(userId, toUserId, rating, comments);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Feedback submitted successfully",
                "feedback", feedback
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserFeedback(@PathVariable Long userId) {
        try {
            List<Feedback> feedbacks = feedbackService.getUserFeedback(userId);
            double averageRating = feedbackService.getAverageRating(userId);
            return ResponseEntity.ok(Map.of(
                "feedbacks", feedbacks,
                "averageRating", averageRating
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }
}

