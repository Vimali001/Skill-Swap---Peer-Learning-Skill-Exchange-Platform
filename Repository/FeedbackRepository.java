package com.skillswap.repository;

import com.skillswap.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByToUserUserId(Long userId);
    List<Feedback> findByFromUserUserId(Long userId);
}

