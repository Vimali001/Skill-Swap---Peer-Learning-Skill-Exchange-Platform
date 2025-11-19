# SkillSwap - Peer Learning & Skill Exchange Platform

A web application where users can exchange skills — e.g., someone good at Java can teach Java to someone who teaches them Photoshop. **Not just another learning platform — it's barter-based knowledge sharing!**

## 🚀 Features

### Core Modules

1. **User Registration & Login**
   - User registration with email/password
   - Session-based authentication
   - Profile management

2. **Profile Creation**
   - Add skills you can teach
   - Add skills you want to learn
   - Bio and location information

3. **Skill Matching Algorithm** ⭐
   - **Algorithm Logic:**
     - A match is found when:
       - User A can teach X, and User B wants to learn X
       - User B can teach Y, and User A wants to learn Y
   - Smart matching finds perfect skill exchange partners
   - Mutual benefit matching

4. **Chat Request / Session Booking**
   - Send chat requests to matched users
   - Accept/Decline chat requests
   - Direct communication for skill exchange

5. **Feedback & Rating System**
   - Rate your skill exchange partners (1-5 stars)
   - Leave comments and reviews
   - Build reputation in the community

6. **Notifications**
   - Real-time notifications for matches
   - Chat request notifications
   - Match status updates

## 🛠️ Technology Stack

- **Backend**: Spring Boot 3.1.5
- **Frontend**: HTML5, CSS3, JavaScript (Vanilla)
- **Database**: MySQL 8.0
- **ORM**: JPA/Hibernate
- **Build Tool**: Maven

## 📋 Database Design (8 Tables)

1. **users** - User accounts
2. **skills** - Available skills catalog
3. **user_skills_teach** - Skills users can teach
4. **user_skills_learn** - Skills users want to learn
5. **matches** - Skill exchange matches
6. **chat_requests** - Chat/session booking requests
7. **feedback** - User ratings and reviews
8. **notifications** - User notifications

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- Your favorite IDE

### Database Setup

1. Create MySQL database (or let Spring Boot create it):
```sql
CREATE DATABASE skillswap_db;
```

2. Update database credentials in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/skillswap_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
```

3. Tables will be auto-created on startup (DDL auto mode is set to `update`).

### Running the Application

1. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```
   
   Or use your IDE to run `SkillSwapApplication.java` or `FoodTrackerApplication.java` (both are in the same project)

2. **Access the application**:
   - SkillSwap: `http://localhost:8080/skillswap/index.html`
   - Food Tracker: `http://localhost:8080/index.html`

## 📚 API Endpoints

### User APIs
- `POST /api/users/register` - Register new user
- `POST /api/users/login` - User login
- `GET /api/users/current` - Get current user
- `POST /api/users/logout` - User logout

### Profile APIs
- `PUT /api/profile` - Update profile (bio, location)
- `POST /api/profile/skills/teach` - Add skill to teach
- `POST /api/profile/skills/learn` - Add skill to learn
- `GET /api/profile/skills/teach` - Get skills user can teach
- `GET /api/profile/skills/learn` - Get skills user wants to learn
- `DELETE /api/profile/skills/teach/{skillId}` - Remove skill from teach list
- `DELETE /api/profile/skills/learn/{skillId}` - Remove skill from learn list

### Match APIs
- `GET /api/matches` - Find skill exchange matches
- `POST /api/matches` - Create match request
- `GET /api/matches/my` - Get user's matches
- `POST /api/matches/{matchId}/accept` - Accept match
- `POST /api/matches/{matchId}/reject` - Reject match

### Chat Request APIs
- `POST /api/chat-requests` - Send chat request
- `GET /api/chat-requests` - Get user's chat requests
- `GET /api/chat-requests/pending` - Get pending requests
- `POST /api/chat-requests/{requestId}/accept` - Accept chat request
- `POST /api/chat-requests/{requestId}/decline` - Decline chat request

### Skill APIs
- `GET /api/skills` - Get all skills
- `POST /api/skills` - Add new skill

### Feedback APIs
- `POST /api/feedback` - Submit feedback
- `GET /api/feedback/user/{userId}` - Get user feedback

### Notification APIs
- `GET /api/notifications` - Get user notifications
- `GET /api/notifications/unread` - Get unread notifications
- `POST /api/notifications/{notificationId}/read` - Mark as read
- `POST /api/notifications/read-all` - Mark all as read

## 🎨 Frontend Pages

1. **index.html** - Landing page
2. **login.html** - Login page
3. **register.html** - Registration page
4. **dashboard.html** - User dashboard
5. **profile.html** - Profile management (add skills)
6. **matches.html** - Find and manage matches
7. **chatRequests.html** - Manage chat requests

## 🔍 Matching Algorithm Details

The matching algorithm finds users who can mutually benefit from skill exchange:

1. For each skill the user can teach:
   - Find users who want to learn that skill
   - For each potential match:
     - Check if they can teach something the user wants to learn
     - If yes, create a match

2. Matches are bidirectional:
   - User A teaches X to User B
   - User B teaches Y to User A

3. Both users must have complementary skills to create a match.

## 💡 Key Features

- **Barter-based learning** - No money, just knowledge exchange
- **Smart matching** - Algorithm finds perfect skill exchange partners
- **Real-time notifications** - Stay updated on matches and requests
- **Feedback system** - Build reputation through ratings
- **Easy communication** - Chat requests for skill exchange sessions

## 📝 Notes

- The application uses session-based authentication
- Password is stored in plain text (for demo purposes - use password hashing in production)
- Matching algorithm ensures mutual benefit
- Chat requests are linked to accepted matches

## 🐛 Troubleshooting

1. **Database Connection Error**:
   - Verify MySQL is running
   - Check credentials in `application.properties`
   - Ensure database exists

2. **Port 8080 already in use**:
   - Change port in `application.properties`: `server.port=8081`

3. **Tables not created**:
   - Check `spring.jpa.hibernate.ddl-auto=update` in `application.properties`
   - Review application logs for errors

## 📄 License

This project is open source and available for educational purposes.

---

**Made with ❤️ for peer learning and skill exchange**


