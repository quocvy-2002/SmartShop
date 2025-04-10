package com.example.SmartShop.repository;

import com.example.SmartShop.entity.Product;
import com.example.SmartShop.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Integer> {
    // đếm số lượng người dùng yêu thích sản phẩm
    @Query("SELECT COUNT(w) FROM WishList w WHERE w.product.productId = :productId")
    Long countByProductId(@Param("productId") Integer productId);

    // lấy ra bản ghni gần nhất có id trùng với id truyền vào
    @Query("SELECT w FROM WishList w WHERE w.product = :product ORDER BY w.created_at DESC")
    Optional<WishList> findTopByProductOrderByCreatedAtDesc(@Param("product") Product product);


}
