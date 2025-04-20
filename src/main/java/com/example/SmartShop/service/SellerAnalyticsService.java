package com.example.SmartShop.service;

import com.example.SmartShop.dto.response.*;
import com.example.SmartShop.entity.*;
import com.example.SmartShop.exception.AppException;
import com.example.SmartShop.exception.ErrorCode;
import com.example.SmartShop.mapper.AnalyticsMapper;
import com.example.SmartShop.repository.OrderRepository;
import com.example.SmartShop.repository.StoreRepository;
import com.example.SmartShop.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class SellerAnalyticsService {
    UserRepository userRepository;
    StoreRepository storeRepository;



    public SellerAnalyticsResponse getSellerAnalytics(Integer userId) {
        var context = SecurityContextHolder.getContext();
        String userName = context.getAuthentication().getName();
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Store store = storeRepository.findByUserUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.STORE_NOT_EXISTED));

        List<Order> orders = store.getProducts().stream()
                .flatMap(product -> product.getOrderDetails() != null ? product.getOrderDetails().stream() : Stream.empty())
                .map(OrderDetail::getOrder)
                .distinct()
                .collect(Collectors.toList());

        BigDecimal totalRevenue = calculateTotalRevenue(orders);
        Integer totalOrders = orders.size();
        List<TopProductDTO> topProducts = mapTopProducts(store.getProducts());

        return SellerAnalyticsResponse.builder()
                .totalRevenue(totalRevenue)
                .totalOrders(totalOrders)
                .topProducts(topProducts)
                .build();
    }

    public SellerSalesAnalyticsResponse getSellerSalesAnalytics() {
        var context = SecurityContextHolder.getContext();
        String userName = context.getAuthentication().getName();
        Store store = storeRepository.findByUserUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.STORE_NOT_EXISTED));

        List<Order> orders = store.getProducts().stream()
                .flatMap(product -> product.getOrderDetails() != null ? product.getOrderDetails().stream() : Stream.empty())
                .map(OrderDetail::getOrder)
                .distinct()
                .collect(Collectors.toList());

        List<SalesDataDTO> salesData = mapSalesData(orders);
        BigDecimal totalSales = calculateTotalRevenue(orders);

        return SellerSalesAnalyticsResponse.builder()
                .salesData(salesData)
                .totalSales(totalSales)
                .build();
    }

    public SellerDashboardResponse getSellerDashboard(Integer userId) {
        var context = SecurityContextHolder.getContext();
        String userName = context.getAuthentication().getName();
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Store store = storeRepository.findByUserUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.STORE_NOT_EXISTED));

        List<Order> orders = store.getProducts().stream()
                .flatMap(product -> product.getOrderDetails() != null ? product.getOrderDetails().stream() : Stream.empty())
                .map(OrderDetail::getOrder)
                .distinct()
                .collect(Collectors.toList());

        BigDecimal totalRevenue = calculateTotalRevenue(orders);
        Integer totalOrders = orders.size();
        List<RecentOrderDTO> recentOrders = mapRecentOrders(orders);

        return SellerDashboardResponse.builder()
                .storeName(store.getNameStore())
                .storeStatus(store.getStatus())
                .totalRevenue(totalRevenue)
                .totalOrders(totalOrders)
                .recentOrders(recentOrders)
                .build();
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
    private List<SalesDataDTO> mapSalesData(List<Order> orders) {
        if (orders == null || orders.isEmpty()) {
            return Collections.emptyList();
        }
        return orders.stream()
                .filter(order -> order.getCreatedAt() != null)
                .collect(Collectors.groupingBy(
                        order -> order.getCreatedAt().toLocalDate().toString(),
                        Collectors.reducing(BigDecimal.ZERO, Order::getTotalAmount, BigDecimal::add)
                ))
                .entrySet().stream()
                .map(entry -> SalesDataDTO.builder()
                        .date(entry.getKey())
                        .totalSales(entry.getValue())
                        .build())
                .sorted(Comparator.comparing(SalesDataDTO::getDate))
                .collect(Collectors.toList());
    }
    private BigDecimal calculateTotalRevenue(List<Order> orders) {
        if (orders == null || orders.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return orders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    private List<TopProductDTO> mapTopProducts(List<Product> products) {
        if (products == null) {
            return Collections.emptyList();
        }
        return products.stream()
                .map(product -> TopProductDTO.builder()
                        .productId(product.getProductId())
                        .name(product.getProductName())
                        .sales(product.getOrderDetails() != null ? product.getOrderDetails().size() : 0)
                        .build())
                .sorted((p1, p2) -> p2.getSales().compareTo(p1.getSales()))
                .limit(5)
                .collect(Collectors.toList());
    }
}
