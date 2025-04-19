package com.example.SmartShop.repository;

import com.example.SmartShop.entity.Product;
import com.example.SmartShop.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    // lấy ra số lượt đnash giá của 1 sản phẩm với id truyền vòa
    @Query("SELECT COUNT(r) FROM Review r WHERE r.product.productId = :productId")
    Long countByProductId(@Param("productId") Integer productId);

    // tính trung bình điểm đánh giá của sản phẩm được truyền vào
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.productId = :productId")
    Double avgRatingByProductId(@Param("productId") Integer productId);

    //Lấy review mới nhất (gần thời điểm hiện tại nhất) của một sản phẩm cụ thể.
    @Query("SELECT r FROM Review r WHERE r.product = :product ORDER BY r.createdAt DESC")
    Optional<Review> findTopByProductOrderByCreatedAtDesc(@Param("product") Product product);

    Optional<Review> findTopByParentReview_ReviewId(Integer id);

    Review findByReviewId(Integer reviewId);

    List<Review> findAllByParentReview_ReviewId(Integer id);
}