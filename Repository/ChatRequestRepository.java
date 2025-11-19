package com.skillswap.repository;

import com.skillswap.model.ChatRequest;
import com.skillswap.model.ChatRequest.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatRequestRepository extends JpaRepository<ChatRequest, Long> {
    List<ChatRequest> findByToUserUserId(Long userId);
    List<ChatRequest> findByFromUserUserId(Long userId);
    List<ChatRequest> findByToUserUserIdAndStatus(Long userId, RequestStatus status);
    List<ChatRequest> findByFromUserUserIdAndStatus(Long userId, RequestStatus status);
    List<ChatRequest> findByMatchMatchId(Long matchId);
}

