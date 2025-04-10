package com.example.SmartShop.repository;
import com.example.SmartShop.entity.OrderDetail;
import com.example.SmartShop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    //tính tổng số lượng sản phẩm phù hợp với id truyền vào và có thời gian lớn hơn thời gian nhập vào
    @Query("SELECT SUM(od.quantity) FROM OrderDetail od WHERE od.product.productId = :productId AND od.order.createdAt >= :startDate")
    Long sumQuantityByProductIdAndDate(@Param("productId") Integer productId, @Param("startDate") LocalDate startDate);

    // lấy sản phẩm mới nhất trùng vpows sản phẩm được truyền vào
    @Query("SELECT od FROM OrderDetail od WHERE od.product = :product ORDER BY od.order.createdAt DESC")
    Optional<OrderDetail> findTopByProductOrderByOrderCreatedAtDesc(@Param("product") Product product);
}