package com.example.SmartShop.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminOrderAnalyticsResponse {
    private Integer totalOrders;
    private BigDecimal totalRevenue;
    private Map<String, Integer> statusBreakdown;
}

