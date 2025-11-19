package com.skillswap.service;

import com.skillswap.model.Notification;
import com.skillswap.model.User;
import com.skillswap.repository.NotificationRepository;
import com.skillswap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public Notification createNotification(Long userId, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = new Notification(user, message);
        return notificationRepository.save(notification);
    }

    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByUserUserIdOrderByCreatedAtDesc(userId);
    }

    public List<Notification> getUnreadNotifications(Long userId) {
        return notificationRepository.findByUserUserIdAndIsSeenFalse(userId);
    }

    public long getUnreadCount(Long userId) {
        return notificationRepository.countByUserUserIdAndIsSeenFalse(userId);
    }

    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setIsSeen(true);
        notificationRepository.save(notification);
    }

    public void markAllAsRead(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserUserIdAndIsSeenFalse(userId);
        notifications.forEach(n -> n.setIsSeen(true));
        notificationRepository.saveAll(notifications);
    }
}

