package com.example.SmartShop.controller;

import com.example.SmartShop.dto.response.AdminDashboardResponse;
import com.example.SmartShop.dto.response.AdminOrderAnalyticsResponse;
import com.example.SmartShop.dto.response.AdminUserAnalyticsResponse;
import com.example.SmartShop.dto.response.ApiResponse;
import com.example.SmartShop.service.AdminAnalyticsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/admin")
public class AdminAnalyticsController {

    AdminAnalyticsService adminAnalyticsService;

    @GetMapping("/analytics/users")
    ApiResponse<AdminUserAnalyticsResponse> getAdminUserAnalytics() {
        return ApiResponse.<AdminUserAnalyticsResponse>builder()
                .result(adminAnalyticsService.getAdminUserAnalytics())
                .build();
    }

    @GetMapping("/analytics/orders")
    ApiResponse<AdminOrderAnalyticsResponse> getAdminOrderAnalytics() {
        return ApiResponse.<AdminOrderAnalyticsResponse>builder()
                .result(adminAnalyticsService.getAdminOrderAnalytics())
                .build();
    }

    @GetMapping("/dashboard")
    ApiResponse<AdminDashboardResponse> getAdminDashboard() {
        return ApiResponse.<AdminDashboardResponse>builder()
                .result(adminAnalyticsService.getAdminDashboard())
                .build();
    }
}