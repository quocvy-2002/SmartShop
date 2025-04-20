package com.example.SmartShop.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

// Response cho /seller/analytics
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SellerAnalyticsResponse {
    private BigDecimal totalRevenue;
    private Integer totalOrders;
    private List<TopProductDTO> topProducts;


}








