package com.example.SmartShop.controller;

import com.example.SmartShop.dto.request.CreateStoreRequest;
import com.example.SmartShop.dto.request.UpdateStoreRequest;
import com.example.SmartShop.dto.response.ApiResponse;
import com.example.SmartShop.dto.response.StoreResponse;
import com.example.SmartShop.service.StoreService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
@RestController
@RequestMapping("/seller/stores")
public class StoreController {
    StoreService storeService;
    @GetMapping("/my-store")
    ApiResponse<StoreResponse> getMyStore(){
        return ApiResponse.<StoreResponse>builder()
                .result(storeService.getMyStore())
                .build();
    }

    @PostMapping
    ApiResponse<StoreResponse> createStore(@Valid @RequestBody CreateStoreRequest request) {
        return ApiResponse.<StoreResponse>builder()
                .result(storeService.createStore(request))
                .build();
    }

    @GetMapping("/{storeId}")
    ApiResponse<StoreResponse> getStoreById(@PathVariable Integer storeId) {
        return ApiResponse.<StoreResponse>builder()
                .result(storeService.getStoreById(storeId))
                .build();
    }

    @PutMapping("/{storeId}")
    ApiResponse<String> inactiveStore(@PathVariable Integer storeId) {
        storeService.inactiveStore(storeId);
        return ApiResponse.<String>builder()
                .result("Store activated successfully")
                .build();
    }

    @PutMapping("/{storeId}")
    ApiResponse<String> activeStore(@PathVariable Integer storeId) {
        storeService.activeStore(storeId);
        return ApiResponse.<String>builder()
                .result("Store activated successfully")
                .build();
    }

    @PutMapping("/{storeId}")
    ApiResponse<StoreResponse> updateStore(@PathVariable Integer storeId, @Valid @RequestBody UpdateStoreRequest request) {
        return ApiResponse.<StoreResponse>builder()
                .result(storeService.updateStore(storeId, request))
                .build();
    }
}

