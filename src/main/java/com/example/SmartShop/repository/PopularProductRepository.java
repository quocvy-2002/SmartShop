package com.example.SmartShop.repository;

import com.example.SmartShop.entity.PopularProduct;
import com.example.SmartShop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PopularProductRepository extends JpaRepository<PopularProduct, Integer> {
    Optional<PopularProduct> findByProduct(Product product);

    @Query("SELECT pp FROM PopularProduct pp  ORDER BY pp.popularId DESC")
    Optional<PopularProduct> findTopByPopularProductOrderByPopularIdDesc();

    // lấy ra giá trị thời gian gần nhất
    @Query("SELECT MAX(pp.calculatedAt) FROM PopularProduct pp")
    LocalDate findLatestCalculatedAt();

    //Lấy ra danh sách PopularProduct có thời gian khớp với param truyền vào và được sắp xếp từ lớn đêns bé
    @Query("SELECT pp FROM PopularProduct pp WHERE pp.calculatedAt = :latestDate  ORDER BY pp.popularityScore DESC ")
    List<PopularProduct> findByCalculatedAtOrderByPopularityScoreDesc(@Param("latestDate") LocalDate latestDate);
}