package com.skillswap.service;

import com.skillswap.model.*;
import com.skillswap.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private UserSkillTeachRepository userSkillTeachRepository;

    @Autowired
    private UserSkillLearnRepository userSkillLearnRepository;

    public User updateProfile(Long userId, String bio, String location) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (bio != null) {
            user.setBio(bio);
        }
        if (location != null) {
            user.setLocation(location);
        }
        
        return userRepository.save(user);
    }

    @Transactional
    public void addSkillToTeach(Long userId, Long skillId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        if (!userSkillTeachRepository.existsByUserUserIdAndSkillSkillId(userId, skillId)) {
            UserSkillTeach userSkillTeach = new UserSkillTeach(user, skill);
            userSkillTeachRepository.save(userSkillTeach);
        }
    }

    @Transactional
    public void addSkillToLearn(Long userId, Long skillId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        if (!userSkillLearnRepository.existsByUserUserIdAndSkillSkillId(userId, skillId)) {
            UserSkillLearn userSkillLearn = new UserSkillLearn(user, skill);
            userSkillLearnRepository.save(userSkillLearn);
        }
    }

    @Transactional
    public void removeSkillFromTeach(Long userId, Long skillId) {
        userSkillTeachRepository.deleteByUserUserIdAndSkillSkillId(userId, skillId);
    }

    @Transactional
    public void removeSkillFromLearn(Long userId, Long skillId) {
        userSkillLearnRepository.deleteByUserUserIdAndSkillSkillId(userId, skillId);
    }

    public List<UserSkillTeach> getUserSkillsTeach(Long userId) {
        return userSkillTeachRepository.findByUserUserId(userId);
    }

    public List<UserSkillLearn> getUserSkillsLearn(Long userId) {
        return userSkillLearnRepository.findByUserUserId(userId);
    }
}

