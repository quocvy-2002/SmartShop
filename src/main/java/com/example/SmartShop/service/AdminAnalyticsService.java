package com.example.SmartShop.service;


import com.example.SmartShop.dto.response.AdminDashboardResponse;
import com.example.SmartShop.dto.response.AdminOrderAnalyticsResponse;
import com.example.SmartShop.dto.response.AdminUserAnalyticsResponse;
import com.example.SmartShop.dto.response.RecentOrderDTO;
import com.example.SmartShop.entity.Order;
import com.example.SmartShop.entity.Store;
import com.example.SmartShop.entity.User;
import com.example.SmartShop.mapper.AnalyticsMapper;
import com.example.SmartShop.repository.OrderRepository;
import com.example.SmartShop.repository.StoreRepository;
import com.example.SmartShop.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class AdminAnalyticsService {

    UserRepository userRepository;
    StoreRepository storeRepository;
    OrderRepository orderRepository;


    public AdminUserAnalyticsResponse getAdminUserAnalytics() {
        List<User> users = userRepository.findAll();

        Integer totalUsers = users.size();
        Integer activeUsers = (int) users.stream()
                .filter(user -> user.getUpdatedAt() != null &&
                        user.getUpdatedAt().isAfter(LocalDateTime.now().minusDays(30)))
                .count();
        Integer sellers = (int) users.stream()
                .filter(user -> user.getRole() != null && "SELLER".equalsIgnoreCase(user.getRole().getName()))
                .count();
        Integer buyers = (int) users.stream()
                .filter(user -> user.getRole() != null && "USER".equalsIgnoreCase(user.getRole().getName()))
                .count();

        return AdminUserAnalyticsResponse.builder()
                .totalUsers(totalUsers)
                .activeUsers(activeUsers)
                .sellers(sellers)
                .buyers(buyers)
                .build();
    }

    public AdminOrderAnalyticsResponse getAdminOrderAnalytics() {
        List<Order> orders = orderRepository.findAll();

        Integer totalOrders = orders.size();
        BigDecimal totalRevenue = calculateTotalRevenue(orders);
        Map<String, Integer> statusBreakdown = orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getStatus,
                        Collectors.reducing(0, e -> 1, Integer::sum)
                ));

        return AdminOrderAnalyticsResponse.builder()
                .totalOrders(totalOrders)
                .totalRevenue(totalRevenue)
                .statusBreakdown(statusBreakdown)
                .build();
    }

    public AdminDashboardResponse getAdminDashboard() {
        List<User> users = userRepository.findAll();
        List<Store> stores = storeRepository.findAll();
        List<Order> orders = orderRepository.findAll();

        Integer totalUsers = users.size();
        Integer totalStores = stores.size();
        BigDecimal totalRevenue = calculateTotalRevenue(orders);
        Integer pendingOrders = (int) orders.stream()
                .filter(order -> "pending".equalsIgnoreCase(order.getStatus()))
                .count();
        List<RecentOrderDTO> recentOrders = mapRecentOrders(orders);

        return AdminDashboardResponse.builder()
                .totalUsers(totalUsers)
                .totalStores(totalStores)
                .totalRevenue(totalRevenue)
                .pendingOrders(pendingOrders)
                .recentOrders(recentOrders)
                .build();

    }
    private BigDecimal calculateTotalRevenue(List<Order> orders) {
        if (orders == null || orders.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return orders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    private List<RecentOrderDTO> mapRecentOrders(List<Order> orders) {
        if (orders == null) {
            return Collections.emptyList();
        }
        return orders.stream()
                .map(order -> RecentOrderDTO.builder()
                        .orderId(order.getOrderId())
                        .totalAmount(order.getTotalAmount())
                        .status(order.getStatus())
                        .createdAt(order.getCreatedAt())
                        .build())
                .limit(5)
                .collect(Collectors.toList());
    }
}