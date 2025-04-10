package com.example.SmartShop.repository;

import com.example.SmartShop.entity.Product;
import com.example.SmartShop.entity.ProductView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ProductViewRepository extends JpaRepository<ProductView, Integer> {
    // đếm các sản phẩm có id trùng với id truyền vào và tính từ thời điểm thời gian truyền vào trở đi
    @Query("SELECT COUNT(pv) FROM ProductView pv WHERE pv.product.productId = :productId AND pv.viewedAt >= :startDate")
    Long countByProductIdAndDate(@Param("productId") Integer productId, @Param("startDate") LocalDateTime startDate);

    // trtar về bản gi có thời gian xem lớn  trùng với product truyền vào
    @Query("SELECT pv FROM ProductView pv WHERE pv.product = :product ORDER BY pv.viewedAt DESC")
    Optional<ProductView> findTopByProductOrderByViewedAtDesc(@Param("product") Product product);
}