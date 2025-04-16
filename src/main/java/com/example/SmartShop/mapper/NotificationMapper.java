package com.example.SmartShop.mapper;

import com.example.SmartShop.dto.request.CreateNotficationRequest;
import com.example.SmartShop.dto.response.NotificationResponse;
import com.example.SmartShop.entity.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    Notification toNotification(CreateNotficationRequest request);
    NotificationResponse toNotificationResponse(Notification notification);
}
