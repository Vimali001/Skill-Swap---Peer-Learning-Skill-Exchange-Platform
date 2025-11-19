package com.skillswap.controller;

import com.skillswap.model.User;
import com.skillswap.model.UserSkillLearn;
import com.skillswap.model.UserSkillTeach;
import com.skillswap.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "*")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PutMapping
    public ResponseEntity<?> updateProfile(
            @RequestBody Map<String, String> request,
            HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Please login first"
            ));
        }
        try {
            String bio = request.get("bio");
            String location = request.get("location");
            User user = profileService.updateProfile(userId, bio, location);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Profile updated successfully",
                "user", user
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/skills/teach")
    public ResponseEntity<?> addSkillToTeach(
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
            Long skillId = request.get("skillId");
            profileService.addSkillToTeach(userId, skillId);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Skill added to teach list"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/skills/learn")
    public ResponseEntity<?> addSkillToLearn(
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
            Long skillId = request.get("skillId");
            profileService.addSkillToLearn(userId, skillId);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Skill added to learn list"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/skills/teach/{skillId}")
    public ResponseEntity<?> removeSkillFromTeach(
            @PathVariable Long skillId,
            HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Please login first"
            ));
        }
        try {
            profileService.removeSkillFromTeach(userId, skillId);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Skill removed from teach list"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/skills/learn/{skillId}")
    public ResponseEntity<?> removeSkillFromLearn(
            @PathVariable Long skillId,
            HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Please login first"
            ));
        }
        try {
            profileService.removeSkillFromLearn(userId, skillId);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Skill removed from learn list"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/skills/teach")
    public ResponseEntity<?> getUserSkillsTeach(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Please login first"
            ));
        }
        try {
            List<UserSkillTeach> skills = profileService.getUserSkillsTeach(userId);
            return ResponseEntity.ok(skills);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/skills/learn")
    public ResponseEntity<?> getUserSkillsLearn(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Please login first"
            ));
        }
        try {
            List<UserSkillLearn> skills = profileService.getUserSkillsLearn(userId);
            return ResponseEntity.ok(skills);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }
}

