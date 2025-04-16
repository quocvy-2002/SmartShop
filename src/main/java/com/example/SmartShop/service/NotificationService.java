package com.example.SmartShop.service;

import com.example.SmartShop.dto.response.NotificationResponse;
import com.example.SmartShop.entity.Notification;
import com.example.SmartShop.entity.User;
import com.example.SmartShop.exception.AppException;
import com.example.SmartShop.exception.ErrorCode;
import com.example.SmartShop.mapper.NotificationMapper;
import com.example.SmartShop.repository.NotificationRepository;
import com.example.SmartShop.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    NotificationRepository notificationRepository;
    NotificationMapper notificationMapper;
    UserRepository userRepository;

    public List<NotificationResponse> getAllNotification() {
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return notificationRepository.findByUser_UserName(user.getUserName())
                .stream()
                .map(notificationMapper::toNotificationResponse)
                .collect(Collectors.toList());
    }
    public NotificationResponse getNotificationById(Integer notificationId){
        Notification notification = notificationRepository.findByNotificationId(notificationId)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_NOT_EXISTED));
        return notificationMapper.toNotificationResponse(notification);
    }
    public String deleteNotificationById(Integer notificationId){
        Notification notification = notificationRepository.findByNotificationId(notificationId)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_NOT_EXISTED));
        notificationRepository.delete(notification);
        return "Notification deleted";
    }
}
