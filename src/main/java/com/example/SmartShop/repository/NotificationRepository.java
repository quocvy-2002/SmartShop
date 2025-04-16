package com.example.SmartShop.repository;

import com.example.SmartShop.entity.Notification;
import com.example.SmartShop.entity.SeachHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUser_UserName(String userName);
    Optional<Notification> findByNotificationId(Integer notificationId);
}
