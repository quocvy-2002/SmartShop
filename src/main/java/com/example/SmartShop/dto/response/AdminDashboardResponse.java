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
public class AdminDashboardResponse {
    private Integer totalUsers;
    private Integer totalStores;
    private BigDecimal totalRevenue;
    private Integer pendingOrders;
    List<RecentOrderDTO> recentOrders;

}