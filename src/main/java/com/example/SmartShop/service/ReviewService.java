package com.example.SmartShop.service;

import com.example.SmartShop.dto.request.CreateReviewRequest;
import com.example.SmartShop.dto.request.UpdateReviewRequest;
import com.example.SmartShop.dto.response.ReviewResponse;
import com.example.SmartShop.entity.Product;
import com.example.SmartShop.entity.Review;
import com.example.SmartShop.entity.User;
import com.example.SmartShop.exception.AppException;
import com.example.SmartShop.exception.ErrorCode;
import com.example.SmartShop.mapper.ReviewMapper;
import com.example.SmartShop.repository.ProductRepository;
import com.example.SmartShop.repository.ReviewRepository;
import com.example.SmartShop.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class ReviewService {
    ReviewMapper reviewMapper;
    ReviewRepository reviewRepository;
    UserRepository userRepository;
    ProductRepository productRepository;

    public ReviewResponse createChilReview(CreateReviewRequest request, Integer parentReviewId,Integer productId){
        var contex = SecurityContextHolder.getContext();
        var userName = (String)contex.getAuthentication().getPrincipal();
        Optional<User> userObj = userRepository.findByUserName(userName);
        User user = userObj.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Optional<Product> productObj = productRepository.findByProductId(productId);
        Product product = productObj.orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        Review parentReview = reviewRepository.findTopByParentReview_ReviewId(parentReviewId)
                .orElseThrow(() -> new AppException(ErrorCode.PARENT_REVIEW_NOT_EXISED));
        if (!parentReview.getProduct().getProductId().equals(productId)) {
            throw new IllegalArgumentException("Parent review does not belong to this product");
        }
        Review review = Review.builder()
                .user(user)
                .product(product)
                .rating(request.getRating())
                .comment(request.getComment())
                .createdAt(LocalDate.now())
                .parentReview(parentReview)
                .build();
        return reviewMapper.toReviewResponse(reviewRepository.save(review));
    }

    public ReviewResponse createReview(Integer productId,CreateReviewRequest request){
        var contex = SecurityContextHolder.getContext();
        var userName = (String)contex.getAuthentication().getPrincipal();
        Optional<User> userObj = userRepository.findByUserName(userName);
        User user = userObj.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Optional<Product> productObj = productRepository.findByProductId(productId);
        Product product = productObj.orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        Review review = Review.builder()
                .user(user)
                .product(product)
                .rating(request.getRating())
                .comment(request.getComment())
                .createdAt(LocalDate.now())
                .parentReview(null)
                .build();
        return reviewMapper.toReviewResponse(reviewRepository.save(review));
    }

    public ReviewResponse getReview(Integer reviewId){
        Review review = reviewRepository.findByReviewId(reviewId);
        if(review == null){
            throw new AppException(ErrorCode.REVIEW_NOT_EXISTED);
        }
        return reviewMapper.toReviewResponse(review);
    }

    public List<ReviewResponse> getChilReview(Integer parentReviewId){
        Review parentReview = reviewRepository.findById(parentReviewId)
                .orElseThrow(() -> new AppException(ErrorCode.PARENT_REVIEW_NOT_EXISED));
        List<Review> listChilReview = reviewRepository.findAllByParentReview_ReviewId(parentReviewId);
        List<ReviewResponse> responseList = listChilReview.stream()
                .map(reviewMapper::toReviewResponse)
                .collect(Collectors.toList());
        return responseList;
    }

    public List<ReviewResponse> getReviewParentReview(){
        List<Review> listReview = reviewRepository.findAll();
        List<ReviewResponse> responseList = listReview.stream()
                .map(reviewMapper::toReviewResponse)
                .collect(Collectors.toList());
        return responseList;
    }

    public String deleteReview(Integer reviewId){
        if (!reviewRepository.existsById(reviewId)) {
            throw new AppException(ErrorCode.REVIEW_NOT_EXISTED);
        }
        reviewRepository.deleteById(reviewId);
        return "Review deleted";
    }

    public ReviewResponse updateReview(Integer reviewId , UpdateReviewRequest request,Integer productId){
        var contex = SecurityContextHolder.getContext();
        var userName = (String)contex.getAuthentication().getPrincipal();
        Optional<User> userObj = userRepository.findByUserName(userName);
        User user = userObj.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Optional<Product> productObj = productRepository.findByProductId(productId);
        Product product = productObj.orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        Review review = reviewRepository.findByReviewId(reviewId);
        if(review == null){
            throw new AppException(ErrorCode.REVIEW_NOT_EXISTED);
        }
        reviewMapper.updateReview(review,request);
        return reviewMapper.toReviewResponse(reviewRepository.save(review));

    }
}
