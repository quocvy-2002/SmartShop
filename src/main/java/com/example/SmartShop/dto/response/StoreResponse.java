package com.example.SmartShop.dto.response;


import com.example.SmartShop.entity.Analytics;
import com.example.SmartShop.entity.Product;
import com.example.SmartShop.entity.SellerRecommentDation;
import com.example.SmartShop.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StoreResponse  {
    Integer storeId;
    UserRespone userRespone;
    String nameStore;
    String description;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    List<Product> products;
    Analytics analytics;
    List<SellerRecommentDation> sellerRecommentDations;

}
