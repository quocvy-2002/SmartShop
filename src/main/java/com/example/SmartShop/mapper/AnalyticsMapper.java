package com.example.SmartShop.mapper;

import com.example.SmartShop.dto.response.*;
import com.example.SmartShop.entity.Order;
import com.example.SmartShop.entity.Product;
import com.example.SmartShop.entity.Store;
import com.example.SmartShop.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AnalyticsMapper {

    AnalyticsMapper INSTANCE = Mappers.getMapper(AnalyticsMapper.class);

    SellerAnalyticsResponse toSellerAnalyticsResponse(User user);

    SellerSalesAnalyticsResponse toSellerSalesAnalyticsResponse(Store store);

    AdminUserAnalyticsResponse toAdminUserAnalyticsResponse(User user);

    AdminOrderAnalyticsResponse toAdminOrderAnalyticsResponse(Order order);

    @Mapping(target = "storeName", source = "store.nameStore")
    @Mapping(target = "storeStatus", source = "store.status")
    SellerDashboardResponse toSellerDashboardResponse(User user);

    AdminDashboardResponse toAdminDashboardResponse(User user);

    TopProductDTO toTopProductDTO(Product product);

    SalesDataDTO toSalesDataDTO(Order order);

    RecentOrderDTO toRecentOrderDTO(Order order);
}