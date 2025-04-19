package com.example.SmartShop.mapper;

import com.example.SmartShop.dto.request.CreateReviewRequest;
import com.example.SmartShop.dto.request.UpdateReviewRequest;
import com.example.SmartShop.dto.request.UpdateUserRequest;
import com.example.SmartShop.dto.response.ReviewResponse;
import com.example.SmartShop.entity.Review;
import com.example.SmartShop.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review toReview(CreateReviewRequest request);
    ReviewResponse toReviewResponse(Review review);
    void updateReview(@MappingTarget Review review, UpdateReviewRequest request);
}
