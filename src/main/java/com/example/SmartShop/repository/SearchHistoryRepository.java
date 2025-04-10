package com.example.SmartShop.repository;

import com.example.SmartShop.entity.SeachHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SeachHistory, Integer> {
    //Đếm số lần tìm kiếm có chứa tên sản phẩm (không phân biệt chữ hoa/chữ thường) được thực hiện kể từ một thời điểm cụ thể trở đi.
    @Query("SELECT COUNT(sh) FROM SeachHistory sh WHERE LOWER(sh.query) LIKE LOWER(CONCAT('%', :productName, '%')) AND sh.createdAt >= :startDate")
    Long countByProductNameAndDate(@Param("productName") String productName, @Param("startDate") LocalDate startDate);

    // lấy ra  bảng ghi ko phân biệt chữ hoa chữ thường được thực hiện mới nhất
    @Query("SELECT sh FROM SeachHistory sh WHERE LOWER(sh.query) LIKE LOWER(CONCAT('%', :productName, '%')) ORDER BY sh.createdAt DESC")
    Optional<SeachHistory> findTopByProductNameOrderByCreatedAtDesc(@Param("productName") String productName);
}