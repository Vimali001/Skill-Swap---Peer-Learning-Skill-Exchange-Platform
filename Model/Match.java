package com.skillswap.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Long matchId;

    @ManyToOne
    @JoinColumn(name = "user1_id", nullable = false)
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2_id", nullable = false)
    private User user2;

    @ManyToOne
    @JoinColumn(name = "skill_teach_user1")
    private Skill skillTeachUser1;

    @ManyToOne
    @JoinColumn(name = "skill_teach_user2")
    private Skill skillTeachUser2;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MatchStatus status = MatchStatus.PENDING;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum MatchStatus {
        PENDING, ACCEPTED, REJECTED
    }

    // Constructors
    public Match() {}

    public Match(User user1, User user2, Skill skillTeachUser1, Skill skillTeachUser2) {
        this.user1 = user1;
        this.user2 = user2;
        this.skillTeachUser1 = skillTeachUser1;
        this.skillTeachUser2 = skillTeachUser2;
    }

    // Getters and Setters
    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public Skill getSkillTeachUser1() {
        return skillTeachUser1;
    }

    public void setSkillTeachUser1(Skill skillTeachUser1) {
        this.skillTeachUser1 = skillTeachUser1;
    }

    public Skill getSkillTeachUser2() {
        return skillTeachUser2;
    }

    public void setSkillTeachUser2(Skill skillTeachUser2) {
        this.skillTeachUser2 = skillTeachUser2;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

