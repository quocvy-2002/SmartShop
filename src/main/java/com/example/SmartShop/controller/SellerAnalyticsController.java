package com.example.SmartShop.controller;


import com.example.SmartShop.dto.response.ApiResponse;
import com.example.SmartShop.dto.response.SellerAnalyticsResponse;
import com.example.SmartShop.dto.response.SellerDashboardResponse;
import com.example.SmartShop.dto.response.SellerSalesAnalyticsResponse;
import com.example.SmartShop.service.SellerAnalyticsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/seller")

public class SellerAnalyticsController {

    SellerAnalyticsService sellerAnalyticsService;

    @GetMapping("/analytics")
    ApiResponse<SellerAnalyticsResponse> getSellerAnalytics(@RequestParam("userId") Integer userId) {
        return ApiResponse.<SellerAnalyticsResponse>builder()
                .result(sellerAnalyticsService.getSellerAnalytics(userId))
                .build();
    }

    @GetMapping("/analytics/sales")
    ApiResponse<SellerSalesAnalyticsResponse> getSellerSalesAnalytics(@RequestParam("storeId") Integer storeId) {
        return ApiResponse.<SellerSalesAnalyticsResponse>builder()
                .result(sellerAnalyticsService.getSellerSalesAnalytics())
                .build();
    }

    @GetMapping("/dashboard")
    ApiResponse<SellerDashboardResponse> getSellerDashboard(@RequestParam("userId") Integer userId) {
        return ApiResponse.<SellerDashboardResponse>builder()
                .result(sellerAnalyticsService.getSellerDashboard(userId))
                .build();
    }
}