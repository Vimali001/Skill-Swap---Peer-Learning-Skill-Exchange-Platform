package com.skillswap.service;

import com.skillswap.model.Skill;
import com.skillswap.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public Skill getSkillById(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found"));
    }

    public Skill getOrCreateSkill(String skillName) {
        return skillRepository.findBySkillName(skillName)
                .orElseGet(() -> {
                    Skill newSkill = new Skill(skillName);
                    return skillRepository.save(newSkill);
                });
    }

    public Skill addSkill(String skillName) {
        if (skillRepository.existsBySkillName(skillName)) {
            throw new RuntimeException("Skill already exists");
        }
        Skill skill = new Skill(skillName);
        return skillRepository.save(skill);
    }
}

