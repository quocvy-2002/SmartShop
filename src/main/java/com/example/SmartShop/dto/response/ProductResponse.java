package com.example.SmartShop.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    private Integer storeId;
    private Integer productGroupId;
    private String productName;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String status;
}
