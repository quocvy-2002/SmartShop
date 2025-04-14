package com.example.SmartShop.controller;

import com.example.SmartShop.dto.response.ApiResponse;
import com.example.SmartShop.dto.response.StoreResponse;
import com.example.SmartShop.service.StoreService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
