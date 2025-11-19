package com.skillswap.controller;

import com.skillswap.model.Skill;
import com.skillswap.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin(origins = "*")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @GetMapping
    public ResponseEntity<?> getAllSkills() {
        try {
            List<Skill> skills = skillService.getAllSkills();
            return ResponseEntity.ok(skills);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }

    @PostMapping
    public ResponseEntity<?> addSkill(@RequestBody Map<String, String> request) {
        try {
            String skillName = request.get("skillName");
            Skill skill = skillService.addSkill(skillName);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Skill added successfully",
                "skill", skill
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSkillById(@PathVariable Long id) {
        try {
            Skill skill = skillService.getSkillById(id);
            return ResponseEntity.ok(skill);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }
}

