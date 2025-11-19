package com.skillswap.controller;

import com.skillswap.model.ChatRequest;
import com.skillswap.service.ChatRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat-requests")
@CrossOrigin(origins = "*")
public class ChatRequestController {

    @Autowired
    private ChatRequestService chatRequestService;

    @PostMapping
    public ResponseEntity<?> createChatRequest(
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
            Long matchId = request.get("matchId") != null ? Long.valueOf(request.get("matchId").toString()) : null;
            String message = (String) request.get("message");
            
            ChatRequest chatRequest = chatRequestService.createChatRequest(userId, toUserId, matchId, message);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Chat request sent successfully",
                "chatRequest", chatRequest
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }

    @GetMapping
    public ResponseEntity<?> getMyChatRequests(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Please login first"
            ));
        }
        try {
            List<ChatRequest> requests = chatRequestService.getUserChatRequests(userId);
            return ResponseEntity.ok(requests);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/pending")
    public ResponseEntity<?> getPendingChatRequests(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Please login first"
            ));
        }
        try {
            List<ChatRequest> requests = chatRequestService.getPendingChatRequests(userId);
            return ResponseEntity.ok(requests);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/{requestId}/accept")
    public ResponseEntity<?> acceptChatRequest(
            @PathVariable Long requestId,
            HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Please login first"
            ));
        }
        try {
            ChatRequest request = chatRequestService.acceptChatRequest(requestId);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Chat request accepted",
                "chatRequest", request
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/{requestId}/decline")
    public ResponseEntity<?> declineChatRequest(
            @PathVariable Long requestId,
            HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Please login first"
            ));
        }
        try {
            ChatRequest request = chatRequestService.declineChatRequest(requestId);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Chat request declined",
                "chatRequest", request
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }
}

