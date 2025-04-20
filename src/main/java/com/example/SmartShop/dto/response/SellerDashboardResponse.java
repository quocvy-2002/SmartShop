package com.example.SmartShop.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SellerDashboardResponse {
    private String storeName;
    private String storeStatus;
    private BigDecimal totalRevenue;
    private Integer totalOrders;
    private List<RecentOrderDTO> recentOrders;
}