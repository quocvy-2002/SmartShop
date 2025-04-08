package com.example.SmartShop.dto.response;

import com.example.SmartShop.entity.CategoryTrend;
import com.example.SmartShop.entity.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductGroupResponse {
    Integer productGroupId;
    String productGroupName;
    String description;
    List<Product> products;
    List<CategoryTrend> categoryTrends;
}
