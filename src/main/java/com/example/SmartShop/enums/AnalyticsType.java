package com.example.SmartShop.enums;

public enum AnalyticsType {
    REVENUE,           // Thống kê doanh thu (ví dụ: doanh thu hàng ngày, hàng tháng của seller)
    TOTAL_ORDERS,      // Tổng số đơn hàng (có thể tính theo seller hoặc toàn hệ thống)
    TOTAL_USERS,       // Tổng số người dùng (toàn hệ thống hoặc theo seller)
    TOTAL_PRODUCTS,    // Tổng số sản phẩm (của một seller hoặc toàn hệ thống)
    AVERAGE_ORDER_VALUE, // Giá trị trung bình của mỗi đơn hàng
    USER_GROWTH,       // Tốc độ tăng trưởng người dùng
    ORDER_GROWTH,      // Tốc độ tăng trưởng đơn hàng
    TOP_PRODUCTS,      // Sản phẩm bán chạy nhất
    CUSTOMER_RETENTION // Tỷ lệ khách hàng quay lại
}