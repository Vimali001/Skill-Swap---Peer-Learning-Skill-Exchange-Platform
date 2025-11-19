package com.skillswap.service;

import com.skillswap.model.*;
import com.skillswap.model.Match.MatchStatus;
import com.skillswap.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchingService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSkillTeachRepository userSkillTeachRepository;

    @Autowired
    private UserSkillLearnRepository userSkillLearnRepository;

    @Autowired
    private SkillRepository skillRepository;

    /**
     * Find mutual skill exchange matches for a user
     * A match is found when:
     * - User A can teach X, and User B wants to learn X
     * - User B can teach Y, and User A wants to learn Y
     */
    public List<Map<String, Object>> findMatches(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<UserSkillTeach> userTeaches = userSkillTeachRepository.findByUserUserId(userId);
        List<UserSkillLearn> userLearns = userSkillLearnRepository.findByUserUserId(userId);

        if (userTeaches.isEmpty() || userLearns.isEmpty()) {
            return new ArrayList<>();
        }

        Set<Long> matchedUserIds = new HashSet<>();
        List<Map<String, Object>> matches = new ArrayList<>();

        // For each skill user can teach
        for (UserSkillTeach teach : userTeaches) {
            Long skillId = teach.getSkill().getSkillId();

            // Find users who want to learn this skill
            List<UserSkillLearn> learners = userSkillLearnRepository.findAll().stream()
                    .filter(learn -> learn.getSkill().getSkillId().equals(skillId))
                    .filter(learn -> !learn.getUser().getUserId().equals(userId))
                    .collect(Collectors.toList());

            for (UserSkillLearn learner : learners) {
                Long potentialMatchUserId = learner.getUser().getUserId();

                // Check if this user can teach something we want to learn
                List<UserSkillTeach> potentialMatchTeaches = userSkillTeachRepository.findByUserUserId(potentialMatchUserId);
                
                for (UserSkillLearn userLearn : userLearns) {
                    for (UserSkillTeach matchTeach : potentialMatchTeaches) {
                        if (matchTeach.getSkill().getSkillId().equals(userLearn.getSkill().getSkillId())) {
                            // Found a match!
                            if (!matchedUserIds.contains(potentialMatchUserId)) {
                                matchedUserIds.add(potentialMatchUserId);
                                
                                // Check if match already exists
                                boolean matchExists = matchRepository.existsByUser1UserIdAndUser2UserId(
                                        Math.min(userId, potentialMatchUserId),
                                        Math.max(userId, potentialMatchUserId)
                                );

                                if (!matchExists) {
                                    Map<String, Object> match = new HashMap<>();
                                    match.put("user", learner.getUser());
                                    match.put("canTeach", matchTeach.getSkill().getSkillName());
                                    match.put("wantsToLearn", teach.getSkill().getSkillName());
                                    match.put("skillTeachId", matchTeach.getSkill().getSkillId());
                                    match.put("skillLearnId", skillId);
                                    matches.add(match);
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }

        return matches;
    }

    @Transactional
    public Match createMatch(Long user1Id, Long user2Id, Long skillTeachUser1Id, Long skillTeachUser2Id) {
        User user1 = userRepository.findById(user1Id)
                .orElseThrow(() -> new RuntimeException("User1 not found"));
        User user2 = userRepository.findById(user2Id)
                .orElseThrow(() -> new RuntimeException("User2 not found"));
        Skill skill1 = skillRepository.findById(skillTeachUser1Id)
                .orElseThrow(() -> new RuntimeException("Skill1 not found"));
        Skill skill2 = skillRepository.findById(skillTeachUser2Id)
                .orElseThrow(() -> new RuntimeException("Skill2 not found"));

        Match match = new Match(user1, user2, skill1, skill2);
        match.setStatus(MatchStatus.PENDING);
        return matchRepository.save(match);
    }

    public List<Match> getUserMatches(Long userId) {
        return matchRepository.findByUser1UserIdOrUser2UserId(userId, userId);
    }

    @Transactional
    public Match acceptMatch(Long matchId, Long userId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found"));

        // Verify user is part of this match
        if (!match.getUser1().getUserId().equals(userId) && !match.getUser2().getUserId().equals(userId)) {
            throw new RuntimeException("User is not part of this match");
        }

        match.setStatus(MatchStatus.ACCEPTED);
        return matchRepository.save(match);
    }

    @Transactional
    public Match rejectMatch(Long matchId, Long userId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found"));

        if (!match.getUser1().getUserId().equals(userId) && !match.getUser2().getUserId().equals(userId)) {
            throw new RuntimeException("User is not part of this match");
        }

        match.setStatus(MatchStatus.REJECTED);
        return matchRepository.save(match);
    }
}

