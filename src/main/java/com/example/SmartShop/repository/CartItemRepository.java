package com.example.SmartShop.repository;

import com.example.SmartShop.entity.CartItem;
import com.example.SmartShop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    // đếm các số lượng sản phẩm được lưu vào giỏ hàng phù hợp với id sản phẩm truyền vào
    @Query("SELECT COUNT(ci) FROM CartItem ci WHERE ci.product.productId = :productId")
    Long countByProductId(@Param("productId") Integer productId);

    // lấy bảng ghi mới nhất có chứa sản phẩm cụ thể, dựa theo thời gian cập nhật của của hàng
    @Query("SELECT ci FROM CartItem ci WHERE ci.product = :product ORDER BY ci.cart.updatedAt DESC")
    Optional<CartItem> findTopByProductOrderByCartUpdatedAtDesc(@Param("product") Product product);
}