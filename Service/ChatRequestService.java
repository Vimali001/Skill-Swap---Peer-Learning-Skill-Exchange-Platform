package com.skillswap.service;

import com.skillswap.model.*;
import com.skillswap.model.ChatRequest.RequestStatus;
import com.skillswap.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatRequestService {

    @Autowired
    private ChatRequestRepository chatRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Transactional
    public ChatRequest createChatRequest(Long fromUserId, Long toUserId, Long matchId, String message) {
        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new RuntimeException("From user not found"));
        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new RuntimeException("To user not found"));
        
        Match match = null;
        if (matchId != null) {
            match = matchRepository.findById(matchId)
                    .orElseThrow(() -> new RuntimeException("Match not found"));
        }

        ChatRequest request = new ChatRequest(fromUser, toUser, match, message);
        request.setStatus(RequestStatus.PENDING);

        // Create notification
        Notification notification = new Notification(toUser, 
            fromUser.getName() + " sent you a chat request");
        notificationRepository.save(notification);

        return chatRequestRepository.save(request);
    }

    public List<ChatRequest> getUserChatRequests(Long userId) {
        return chatRequestRepository.findByToUserUserId(userId);
    }

    public List<ChatRequest> getPendingChatRequests(Long userId) {
        return chatRequestRepository.findByToUserUserIdAndStatus(userId, RequestStatus.PENDING);
    }

    @Transactional
    public ChatRequest acceptChatRequest(Long requestId) {
        ChatRequest request = chatRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Chat request not found"));

        request.setStatus(RequestStatus.ACCEPTED);
        return chatRequestRepository.save(request);
    }

    @Transactional
    public ChatRequest declineChatRequest(Long requestId) {
        ChatRequest request = chatRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Chat request not found"));

        request.setStatus(RequestStatus.DECLINED);
        return chatRequestRepository.save(request);
    }
}

