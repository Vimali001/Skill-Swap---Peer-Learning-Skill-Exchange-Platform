package com.skillswap.repository;

import com.skillswap.model.UserSkillLearn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserSkillLearnRepository extends JpaRepository<UserSkillLearn, Long> {
    List<UserSkillLearn> findByUserUserId(Long userId);
    void deleteByUserUserIdAndSkillSkillId(Long userId, Long skillId);
    boolean existsByUserUserIdAndSkillSkillId(Long userId, Long skillId);
}

