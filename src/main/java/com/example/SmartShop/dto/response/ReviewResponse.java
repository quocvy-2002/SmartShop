package com.example.SmartShop.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ReviewResponse {
    Integer reviewId;
    String userName;
    String comment;
    Integer productId;
    String productName;
    Integer rating;
    LocalDate createdAt;
    Integer parentReviewId;
    List<ReviewResponse> childReviews;
}
