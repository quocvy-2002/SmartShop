package com.example.SmartShop.enums;

public enum OrderStatus {
    PENDING,        // Đơn hàng vừa được tạo, đang chờ xử lý
    CONFIRMED,      // Đơn hàng đã được xác nhận
    SHIPPING,       // Đơn hàng đang được giao
    DELIVERED,      // Đơn hàng đã giao thành công
    COMPLETED,      // Đơn hàng hoàn tất (thanh toán xong, không có vấn đề)
    CANCELLED,      // Đơn hàng bị hủy
    RETURNED,       // Đơn hàng bị trả lại
    FAILED          // Đơn hàng thất bại (ví dụ: không thanh toán được)
}