package com.example.SmartShop.controller;

import com.example.SmartShop.dto.response.ApiResponse;
import com.example.SmartShop.dto.response.NotificationResponse;
import com.example.SmartShop.service.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
@RestController
@RequestMapping("/notifications")
public class NotificationController {
    NotificationService notificationService;

    @GetMapping
    ApiResponse<List<NotificationResponse>> GetAllNotification(){
        return ApiResponse.<List<NotificationResponse>>builder()
                .result(notificationService.getAllNotification())
                .build();
    }

    @GetMapping("/{notificationId}")
    ApiResponse<NotificationResponse> GetNotificationById(@PathVariable("notificationId") Integer notificationId){
        return ApiResponse.<NotificationResponse>builder()
                .result(notificationService.getNotificationById(notificationId))
                .build();
    }

    @DeleteMapping("/{notificationId}")
    ApiResponse<String> deleteNotificationById(@PathVariable("notificationId") Integer notificationId){
        notificationService.deleteNotificationById(notificationId);
        return ApiResponse.<String>builder()
                .result("Notification has been deleted")
                .build();
    }
}
