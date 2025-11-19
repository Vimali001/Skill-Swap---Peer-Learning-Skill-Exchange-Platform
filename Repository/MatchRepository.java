package com.skillswap.repository;

import com.skillswap.model.Match;
import com.skillswap.model.Match.MatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByUser1UserIdOrUser2UserId(Long user1Id, Long user2Id);
    List<Match> findByUser1UserIdAndStatus(Long userId, MatchStatus status);
    List<Match> findByUser2UserIdAndStatus(Long userId, MatchStatus status);
    List<Match> findByStatus(MatchStatus status);
    boolean existsByUser1UserIdAndUser2UserId(Long user1Id, Long user2Id);
}

