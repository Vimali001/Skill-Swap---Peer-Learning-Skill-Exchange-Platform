package com.skillswap.controller;

import com.skillswap.model.Match;
import com.skillswap.service.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/matches")
@CrossOrigin(origins = "*")
public class MatchController {

    @Autowired
    private MatchingService matchingService;

    @GetMapping
    public ResponseEntity<?> findMatches(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Please login first"
            ));
        }
        try {
            List<Map<String, Object>> matches = matchingService.findMatches(userId);
            return ResponseEntity.ok(matches);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }

    @PostMapping
    public ResponseEntity<?> createMatch(
            @RequestBody Map<String, Long> request,
            HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Please login first"
            ));
        }
        try {
            Long user2Id = request.get("user2Id");
            Long skillTeachUser1Id = request.get("skillTeachUser1Id");
            Long skillTeachUser2Id = request.get("skillTeachUser2Id");
            
            Match match = matchingService.createMatch(userId, user2Id, skillTeachUser1Id, skillTeachUser2Id);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Match created successfully",
                "match", match
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyMatches(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Please login first"
            ));
        }
        try {
            List<Match> matches = matchingService.getUserMatches(userId);
            return ResponseEntity.ok(matches);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/{matchId}/accept")
    public ResponseEntity<?> acceptMatch(
            @PathVariable Long matchId,
            HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Please login first"
            ));
        }
        try {
            Match match = matchingService.acceptMatch(matchId, userId);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Match accepted successfully",
                "match", match
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/{matchId}/reject")
    public ResponseEntity<?> rejectMatch(
            @PathVariable Long matchId,
            HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Please login first"
            ));
        }
        try {
            Match match = matchingService.rejectMatch(matchId, userId);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Match rejected",
                "match", match
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }
}

