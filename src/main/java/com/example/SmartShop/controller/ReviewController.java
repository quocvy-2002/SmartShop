package com.example.SmartShop.controller;

import com.example.SmartShop.dto.request.CreateReviewRequest;
import com.example.SmartShop.dto.request.UpdateReviewRequest;
import com.example.SmartShop.dto.response.ApiResponse;
import com.example.SmartShop.dto.response.ReviewResponse;
import com.example.SmartShop.service.ReviewService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/reviews")
public class ReviewController {
    ReviewService reviewService;

    @PostMapping("/{productId}")
    ApiResponse<ReviewResponse> createReview(@RequestBody CreateReviewRequest request,@PathVariable Integer productId) {
        return ApiResponse.<ReviewResponse>builder()
                .result(reviewService.createReview(productId,request))
                .build();
    }

    @PostMapping("/{productId}/reply/{parentReviewId}")
    ApiResponse<ReviewResponse> createChildReview(
            @PathVariable Integer productId,
            @PathVariable Integer parentReviewId,
            @RequestBody CreateReviewRequest request
    ){
        return ApiResponse.<ReviewResponse>builder()
                .result(reviewService.createChilReview(request,productId,parentReviewId))
                .build();
    }

    @GetMapping("/{reviewId}")
    ApiResponse<ReviewResponse> getReview(@PathVariable Integer reviewId) {
        return ApiResponse.<ReviewResponse>builder()
                .result(reviewService.getReview(reviewId))
                .build();
    }

    @GetMapping("/child/{parentReviewId}")
    ApiResponse<List<ReviewResponse>> getChildReview(@PathVariable Integer parentReviewId) {
        return ApiResponse.<List<ReviewResponse>>builder()
                .result(reviewService.getChilReview(parentReviewId))
                .build();
    }

    @GetMapping
    ApiResponse<List<ReviewResponse>> getAllReviews() {
        return ApiResponse.<List<ReviewResponse>>builder()
                .result(reviewService.getReviewParentReview())
                .build();
    }

    @DeleteMapping("/{reviewId}")
    ApiResponse<String> deleteReview(@PathVariable Integer reviewId) {
        return  ApiResponse.<String>builder()
                .result(reviewService.deleteReview(reviewId))
                .build();
    }

    @PutMapping("/{productId}/{reviewId}")
    ApiResponse<ReviewResponse> updateReview( @PathVariable Integer reviewId,
                                              @PathVariable Integer productId,
                                              @RequestBody UpdateReviewRequest request){
        return ApiResponse.<ReviewResponse>builder()
                .result(reviewService.updateReview(reviewId,request,productId))
                .build();
    }
}
