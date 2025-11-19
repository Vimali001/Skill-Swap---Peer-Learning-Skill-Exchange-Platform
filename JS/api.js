// API Base URL
const API_BASE_URL = '/api';

// Helper function for API calls
async function apiCall(endpoint, method = 'GET', body = null) {
    const options = {
        method: method,
        headers: {
            'Content-Type': 'application/json',
        },
        credentials: 'include' // Important for session cookies
    };

    if (body) {
        options.body = JSON.stringify(body);
    }

    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, options);
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('API Error:', error);
        throw error;
    }
}

// User API
const userAPI = {
    register: (userData) => apiCall('/users/register', 'POST', userData),
    login: (credentials) => apiCall('/users/login', 'POST', credentials),
    logout: () => apiCall('/users/logout', 'POST'),
    getCurrent: () => apiCall('/users/current'),
    getById: (id) => apiCall(`/users/${id}`)
};

// Skill API
const skillAPI = {
    getAll: () => apiCall('/skills'),
    add: (skillName) => apiCall('/skills', 'POST', { skillName }),
    getById: (id) => apiCall(`/skills/${id}`)
};

// Profile API
const profileAPI = {
    update: (bio, location) => apiCall('/profile', 'PUT', { bio, location }),
    addSkillToTeach: (skillId) => apiCall('/profile/skills/teach', 'POST', { skillId }),
    addSkillToLearn: (skillId) => apiCall('/profile/skills/learn', 'POST', { skillId }),
    removeSkillFromTeach: (skillId) => apiCall(`/profile/skills/teach/${skillId}`, 'DELETE'),
    removeSkillFromLearn: (skillId) => apiCall(`/profile/skills/learn/${skillId}`, 'DELETE'),
    getSkillsTeach: () => apiCall('/profile/skills/teach'),
    getSkillsLearn: () => apiCall('/profile/skills/learn')
};

// Match API
const matchAPI = {
    findMatches: () => apiCall('/matches'),
    createMatch: (user2Id, skillTeachUser1Id, skillTeachUser2Id) => 
        apiCall('/matches', 'POST', { user2Id, skillTeachUser1Id, skillTeachUser2Id }),
    getMyMatches: () => apiCall('/matches/my'),
    accept: (matchId) => apiCall(`/matches/${matchId}/accept`, 'POST'),
    reject: (matchId) => apiCall(`/matches/${matchId}/reject`, 'POST')
};

// Chat Request API
const chatRequestAPI = {
    create: (toUserId, matchId, message) => 
        apiCall('/chat-requests', 'POST', { toUserId, matchId, message }),
    getMyRequests: () => apiCall('/chat-requests'),
    getPending: () => apiCall('/chat-requests/pending'),
    accept: (requestId) => apiCall(`/chat-requests/${requestId}/accept`, 'POST'),
    decline: (requestId) => apiCall(`/chat-requests/${requestId}/decline`, 'POST')
};

// Feedback API
const feedbackAPI = {
    add: (toUserId, rating, comments) => 
        apiCall('/feedback', 'POST', { toUserId, rating, comments }),
    getUserFeedback: (userId) => apiCall(`/feedback/user/${userId}`)
};

// Notification API
const notificationAPI = {
    getAll: () => apiCall('/notifications'),
    getUnread: () => apiCall('/notifications/unread'),
    markAsRead: (notificationId) => apiCall(`/notifications/${notificationId}/read`, 'POST'),
    markAllAsRead: () => apiCall('/notifications/read-all', 'POST')
};

// Utility functions
function showAlert(message, type = 'info') {
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type}`;
    alertDiv.textContent = message;
    
    const container = document.querySelector('.container') || document.body;
    container.insertBefore(alertDiv, container.firstChild);
    
    setTimeout(() => {
        alertDiv.remove();
    }, 5000);
}

function formatDate(dateString) {
    if (!dateString) return 'N/A';
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric' });
}

function checkAuth() {
    return apiCall('/users/current')
        .then(data => {
            if (data.userId) {
                return true;
            }
            return false;
        })
        .catch(() => false);
}

function redirectIfNotAuth() {
    checkAuth().then(isAuth => {
        if (!isAuth) {
            window.location.href = '/skillswap/login.html';
        }
    });
}

// Store auth info
function setAuthInfo(user) {
    localStorage.setItem('currentUser', JSON.stringify(user));
}

function getAuthInfo() {
    const userStr = localStorage.getItem('currentUser');
    return userStr ? JSON.parse(userStr) : null;
}

function clearAuthInfo() {
    localStorage.removeItem('currentUser');
}

