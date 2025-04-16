package com.example.SmartShop.controller;

import com.example.SmartShop.dto.response.ApiResponse;
import com.example.SmartShop.dto.response.NotificationResponse;
import com.example.SmartShop.dto.response.StoreResponse;
import com.example.SmartShop.entity.Store;
import com.example.SmartShop.service.AdminService;
import com.example.SmartShop.service.StoreService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
@RestController
@RequestMapping("/admin")
public class AdminController {
    AdminService adminService;
    StoreService storeService;

    @GetMapping("/store")
    ApiResponse<List<Store>> getStore(){
        return ApiResponse.<List<Store>>builder()
                .result(adminService.getPendingStore())
                .build();
    }

    @PutMapping("/{storeId}/approve")
    ApiResponse<StoreResponse> approveStore( @PathVariable Integer storeId,
                                             @RequestParam boolean isApproved,
                                             @RequestParam(required = false) String reason){
        return ApiResponse.<StoreResponse>builder()
                .result(adminService.approveStore(storeId, isApproved, reason))
                .build();
    }

    @PostMapping("/notifications")
    ApiResponse<String> getNotifications(@RequestParam String message){
        adminService.notifyAllUsers(message);
        return ApiResponse.<String>builder()
                .result("Notification has been sent")
                .build();
    }

    @GetMapping("/stores")
    ApiResponse<List<Store>> getStores() {
        return ApiResponse.<List<Store>>builder()
                .result(storeService.getAllStores())
                .build();
    }

}
