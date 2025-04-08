package com.example.SmartShop.repository;

import com.example.SmartShop.entity.Product;
import com.example.SmartShop.entity.ProductGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductGroupRepository extends JpaRepository<ProductGroup, Integer> {
    Optional<ProductGroup> findByProductGroupId(Integer productGroupId);

    boolean existsByProductGroupName(String productGroupName);

    boolean existsByProductGroupId(Integer productGroupId);

    @Query("SELECT p FROM Product p WHERE p.productGroup.productGroupId = :productGroupId")
    Page<Product> findByProductGroupId(@Param("productGroupId") Integer productGroupId, Pageable pageable);

}