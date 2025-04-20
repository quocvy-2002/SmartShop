package com.example.SmartShop.repository;

import com.example.SmartShop.dto.response.StoreResponse;
import com.example.SmartShop.entity.Product;
import com.example.SmartShop.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Integer> {
    Store findByStoreId(Integer storeId);
    Optional<Store> findByNameStore(String storeName);
    List<Product>  findAllByNameStore(String storeName);
    List<Store> findByStatus(String status);
    Optional<Store> findByUserUserName(String userName);
}
