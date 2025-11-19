package com.skillswap.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "skills")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Long skillId;

    @NotBlank(message = "Skill name is required")
    @Column(name = "skill_name", nullable = false, unique = true, length = 100)
    private String skillName;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL)
    private List<UserSkillTeach> userSkillsTeach;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL)
    private List<UserSkillLearn> userSkillsLearn;

    // Constructors
    public Skill() {}

    public Skill(String skillName) {
        this.skillName = skillName;
    }

    // Getters and Setters
    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }
}

