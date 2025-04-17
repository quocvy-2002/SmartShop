package com.example.SmartShop.controller;

import com.example.SmartShop.dto.request.CreateWishListRequest;
import com.example.SmartShop.dto.response.ApiResponse;
import com.example.SmartShop.dto.response.WishListResponse;
import com.example.SmartShop.service.WishListService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
@RequestMapping("/wishlist")
public class WishLishController {
    WishListService wishListService;

    @PostMapping
    ApiResponse<WishListResponse> creatWishList(@Valid @RequestBody CreateWishListRequest request) {
        return ApiResponse.<WishListResponse>builder()
                .result(wishListService.createWishList(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<WishListResponse>> getWishList() {
        return ApiResponse.<List<WishListResponse>>builder()
                .result(wishListService.getWishListByUserName())
                .build();
    }

    @DeleteMapping("/{wishListId}")
    ApiResponse<String> deleteWishList(@PathVariable Integer wishListId) {
        return ApiResponse.<String>builder()
                .result(wishListService.removeFromWishList(wishListId))
                .build();
    }

}
