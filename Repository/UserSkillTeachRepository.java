package com.skillswap.repository;

import com.skillswap.model.UserSkillTeach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserSkillTeachRepository extends JpaRepository<UserSkillTeach, Long> {
    List<UserSkillTeach> findByUserUserId(Long userId);
    void deleteByUserUserIdAndSkillSkillId(Long userId, Long skillId);
    boolean existsByUserUserIdAndSkillSkillId(Long userId, Long skillId);
}

