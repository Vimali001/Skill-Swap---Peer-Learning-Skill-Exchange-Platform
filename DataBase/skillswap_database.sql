-- ============================================
-- SkillSwap Database Schema
-- Peer Learning & Skill Exchange Platform
-- ============================================

-- Create Database
CREATE DATABASE IF NOT EXISTS skillswap_db;
USE skillswap_db;

-- ============================================
-- Table 1: users
-- ============================================
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    bio TEXT,
    location VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_location (location)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Table 2: skills
-- ============================================
CREATE TABLE IF NOT EXISTS skills (
    skill_id INT AUTO_INCREMENT PRIMARY KEY,
    skill_name VARCHAR(100) NOT NULL UNIQUE,
    INDEX idx_skill_name (skill_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Table 3: user_skills_teach
-- ============================================
CREATE TABLE IF NOT EXISTS user_skills_teach (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    skill_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (skill_id) REFERENCES skills(skill_id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_skill_teach (user_id, skill_id),
    INDEX idx_user_id (user_id),
    INDEX idx_skill_id (skill_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Table 4: user_skills_learn
-- ============================================
CREATE TABLE IF NOT EXISTS user_skills_learn (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    skill_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (skill_id) REFERENCES skills(skill_id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_skill_learn (user_id, skill_id),
    INDEX idx_user_id (user_id),
    INDEX idx_skill_id (skill_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Table 5: matches
-- ============================================
CREATE TABLE IF NOT EXISTS matches (
    match_id INT AUTO_INCREMENT PRIMARY KEY,
    user1_id INT NOT NULL,
    user2_id INT NOT NULL,
    skill_teach_user1 INT,
    skill_teach_user2 INT,
    status ENUM('PENDING', 'ACCEPTED', 'REJECTED') NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user1_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (user2_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (skill_teach_user1) REFERENCES skills(skill_id) ON DELETE SET NULL,
    FOREIGN KEY (skill_teach_user2) REFERENCES skills(skill_id) ON DELETE SET NULL,
    INDEX idx_user1 (user1_id),
    INDEX idx_user2 (user2_id),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Table 6: chat_requests
-- ============================================
CREATE TABLE IF NOT EXISTS chat_requests (
    request_id INT AUTO_INCREMENT PRIMARY KEY,
    from_user INT NOT NULL,
    to_user INT NOT NULL,
    match_id INT,
    message TEXT,
    status ENUM('PENDING', 'ACCEPTED', 'DECLINED') NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (from_user) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (to_user) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (match_id) REFERENCES matches(match_id) ON DELETE SET NULL,
    INDEX idx_from_user (from_user),
    INDEX idx_to_user (to_user),
    INDEX idx_match_id (match_id),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Table 7: feedback
-- ============================================
CREATE TABLE IF NOT EXISTS feedback (
    feedback_id INT AUTO_INCREMENT PRIMARY KEY,
    from_user INT NOT NULL,
    to_user INT NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comments TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (from_user) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (to_user) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_from_user (from_user),
    INDEX idx_to_user (to_user),
    INDEX idx_rating (rating),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Table 8: notifications
-- ============================================
CREATE TABLE IF NOT EXISTS notifications (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    message TEXT NOT NULL,
    is_seen BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_is_seen (is_seen),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Sample Data (Optional - for testing)
-- ============================================

-- Insert Sample Skills
INSERT INTO skills (skill_name) VALUES
('Java Programming'),
('Python Programming'),
('JavaScript'),
('Web Development'),
('Mobile App Development'),
('UI/UX Design'),
('Photoshop'),
('Graphic Design'),
('Data Science'),
('Machine Learning'),
('SQL/Database'),
('React'),
('Node.js'),
('Spring Boot'),
('Git/GitHub'),
('Project Management'),
('Public Speaking'),
('Writing'),
('Photography'),
('Video Editing'),
('Music Production'),
('Cooking'),
('Language Learning (Spanish)'),
('Language Learning (French)'),
('Yoga'),
('Fitness Training'),
('Digital Marketing'),
('SEO'),
('Content Writing'),
('Business Strategy')
ON DUPLICATE KEY UPDATE skill_name=skill_name;

-- Insert Sample Users (passwords are plain text for demo - use hashing in production)
INSERT INTO users (name, email, password, bio, location) VALUES
('John Doe', 'john@example.com', 'password123', 'Experienced Java developer looking to learn UI/UX design', 'New York'),
('Jane Smith', 'jane@example.com', 'password123', 'UI/UX designer who wants to learn programming', 'Los Angeles'),
('Mike Johnson', 'mike@example.com', 'password123', 'Python expert interested in data science', 'Chicago'),
('Sarah Williams', 'sarah@example.com', 'password123', 'Web developer wanting to learn Photoshop', 'Houston'),
('David Brown', 'david@example.com', 'password123', 'Photoshop expert who wants to learn web development', 'Phoenix')
ON DUPLICATE KEY UPDATE email=email;

-- Insert Sample Skills for Users
-- John teaches Java, wants to learn UI/UX Design
INSERT INTO user_skills_teach (user_id, skill_id) 
SELECT 1, skill_id FROM skills WHERE skill_name = 'Java Programming'
ON DUPLICATE KEY UPDATE user_id=user_id;

INSERT INTO user_skills_learn (user_id, skill_id) 
SELECT 1, skill_id FROM skills WHERE skill_name = 'UI/UX Design'
ON DUPLICATE KEY UPDATE user_id=user_id;

-- Jane teaches UI/UX Design, wants to learn Java
INSERT INTO user_skills_teach (user_id, skill_id) 
SELECT 2, skill_id FROM skills WHERE skill_name = 'UI/UX Design'
ON DUPLICATE KEY UPDATE user_id=user_id;

INSERT INTO user_skills_learn (user_id, skill_id) 
SELECT 2, skill_id FROM skills WHERE skill_name = 'Java Programming'
ON DUPLICATE KEY UPDATE user_id=user_id;

-- Mike teaches Python, wants to learn Data Science
INSERT INTO user_skills_teach (user_id, skill_id) 
SELECT 3, skill_id FROM skills WHERE skill_name = 'Python Programming'
ON DUPLICATE KEY UPDATE user_id=user_id;

INSERT INTO user_skills_learn (user_id, skill_id) 
SELECT 3, skill_id FROM skills WHERE skill_name = 'Data Science'
ON DUPLICATE KEY UPDATE user_id=user_id;

-- Sarah teaches Web Development, wants to learn Photoshop
INSERT INTO user_skills_teach (user_id, skill_id) 
SELECT 4, skill_id FROM skills WHERE skill_name = 'Web Development'
ON DUPLICATE KEY UPDATE user_id=user_id;

INSERT INTO user_skills_learn (user_id, skill_id) 
SELECT 4, skill_id FROM skills WHERE skill_name = 'Photoshop'
ON DUPLICATE KEY UPDATE user_id=user_id;

-- David teaches Photoshop, wants to learn Web Development
INSERT INTO user_skills_teach (user_id, skill_id) 
SELECT 5, skill_id FROM skills WHERE skill_name = 'Photoshop'
ON DUPLICATE KEY UPDATE user_id=user_id;

INSERT INTO user_skills_learn (user_id, skill_id) 
SELECT 5, skill_id FROM skills WHERE skill_name = 'Web Development'
ON DUPLICATE KEY UPDATE user_id=user_id;

-- ============================================
-- Views (Optional - for easier queries)
-- ============================================

-- View: User profiles with skill counts
CREATE OR REPLACE VIEW user_profiles AS
SELECT 
    u.user_id,
    u.name,
    u.email,
    u.location,
    u.bio,
    u.created_at,
    COUNT(DISTINCT ust.id) AS skills_teach_count,
    COUNT(DISTINCT usl.id) AS skills_learn_count
FROM users u
LEFT JOIN user_skills_teach ust ON u.user_id = ust.user_id
LEFT JOIN user_skills_learn usl ON u.user_id = usl.user_id
GROUP BY u.user_id, u.name, u.email, u.location, u.bio, u.created_at;

-- View: Match details
CREATE OR REPLACE VIEW match_details AS
SELECT 
    m.match_id,
    m.status,
    m.created_at,
    u1.user_id AS user1_id,
    u1.name AS user1_name,
    u2.user_id AS user2_id,
    u2.name AS user2_name,
    s1.skill_name AS skill_teach_user1,
    s2.skill_name AS skill_teach_user2
FROM matches m
JOIN users u1 ON m.user1_id = u1.user_id
JOIN users u2 ON m.user2_id = u2.user_id
LEFT JOIN skills s1 ON m.skill_teach_user1 = s1.skill_id
LEFT JOIN skills s2 ON m.skill_teach_user2 = s2.skill_id;

-- ============================================
-- Useful Queries
-- ============================================

-- Find potential matches for a user (Example for user_id = 1)
-- This shows users who want to learn what user 1 can teach
-- AND can teach what user 1 wants to learn
/*
SELECT DISTINCT
    u2.user_id,
    u2.name,
    u2.email,
    u2.location,
    u2.bio,
    st.skill_name AS can_teach_you,
    sl.skill_name AS wants_to_learn_from_you
FROM user_skills_teach ust1
JOIN user_skills_learn usl1 ON ust1.user_id = 1
JOIN user_skills_learn usl2 ON ust1.skill_id = usl2.skill_id
JOIN user_skills_teach ust2 ON usl1.skill_id = ust2.skill_id
JOIN users u2 ON u2.user_id = usl2.user_id
JOIN skills st ON ust2.skill_id = st.skill_id
JOIN skills sl ON ust1.skill_id = sl.skill_id
WHERE ust2.user_id != 1
    AND u2.user_id = usl2.user_id
    AND ust2.user_id = u2.user_id;
*/

-- ============================================
-- Stored Procedures (Optional)
-- ============================================

DELIMITER //

-- Procedure: Get user matches
CREATE PROCEDURE IF NOT EXISTS GetUserMatches(IN p_user_id INT)
BEGIN
    SELECT DISTINCT
        u.user_id,
        u.name,
        u.email,
        u.location,
        u.bio,
        teach_skill.skill_name AS can_teach_you,
        learn_skill.skill_name AS wants_to_learn_from_you
    FROM users u
    JOIN user_skills_learn usl2 ON u.user_id = usl2.user_id
    JOIN user_skills_teach ust2 ON u.user_id = ust2.user_id
    JOIN user_skills_teach ust1 ON ust1.user_id = p_user_id AND ust1.skill_id = usl2.skill_id
    JOIN user_skills_learn usl1 ON usl1.user_id = p_user_id AND usl1.skill_id = ust2.skill_id
    JOIN skills teach_skill ON ust2.skill_id = teach_skill.skill_id
    JOIN skills learn_skill ON ust1.skill_id = learn_skill.skill_id
    WHERE u.user_id != p_user_id;
END //

DELIMITER ;

-- ============================================
-- End of Schema
-- ============================================

-- Show tables
SHOW TABLES;

-- Show table structures
-- DESCRIBE users;
-- DESCRIBE skills;
-- DESCRIBE user_skills_teach;
-- DESCRIBE user_skills_learn;
-- DESCRIBE matches;
-- DESCRIBE chat_requests;
-- DESCRIBE feedback;
-- DESCRIBE notifications;

