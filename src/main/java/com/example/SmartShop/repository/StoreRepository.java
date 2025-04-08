package com.example.SmartShop.repository;

import com.example.SmartShop.entity.Product;
import com.example.SmartShop.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Integer> {
    Optional<Store> findByStoreId(Integer storeId);
    List<Product> findByName(String name);
}
