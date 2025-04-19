package com.example.SmartShop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
@Getter
public enum ErrorCode {
    INVALID_KEY(1001, "Invalid messega key error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1001, "User existed",HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1002, "User not existed",HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1003, "Password must have at least 8 characters, 1 capital letter, 1 number and 1 special character.",HttpStatus.BAD_REQUEST),
    INVALID_USERNAME(1003, "Username must have at least 3 characters.", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL(1003, "Invalid email format.", HttpStatus.BAD_REQUEST),
    INVALID_PHONE_NUMBER(1003, "Phone number must be exactly 10 digits.", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error" , HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED(1004, "Unauthenticated",HttpStatus.BAD_REQUEST),
    STORE_NOT_EXISTED(1002, "store not existed",HttpStatus.BAD_REQUEST),
    STORE_EXISTED(1003, "User already has a store",HttpStatus.BAD_REQUEST),
    PRODUCT_GROUP_NOT_EXISTED(1002, "product group not existed",HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_EXISTED(1002, "product not existed",HttpStatus.BAD_REQUEST),
    PRODUCT_GROUP_EXISTED(1002, "product Group existed",HttpStatus.BAD_REQUEST),
    INVALID_INPUT(1002,"Product group name cannot be empty",HttpStatus.BAD_REQUEST),
    PRODUCT_GROUP_IN_USE(1002,"\"Cannot delete product group because it is associated with products\"",HttpStatus.BAD_REQUEST),
    CALCULATED_AT_NOT_EXISTED_IN_POPULARPRODUCT(1002,"Không tìm thấy dữ liệu calculated_at trong PopularProduct.",HttpStatus.BAD_REQUEST),
    NO_PRODUCT_WITH_CALCULATED_AT(1002,"Không có sản phẩm nào với calculated_at ",HttpStatus.BAD_REQUEST),
    NOTIFICATION_NOT_EXISTED(1001, "Notification not existed",HttpStatus.BAD_REQUEST),
    WISHLIST_EXISTED(1001, "Sản phẩm đã có trong danh sách yêu thích!",HttpStatus.BAD_REQUEST),
    PARENT_REVIEW_NOT_EXISED(1001, "Parent review not found",HttpStatus.BAD_REQUEST),
    WISHLIST_NOT_EXISTED(1001, "danh sách yêu thích chưa tồn tại!",HttpStatus.BAD_REQUEST),
    REVIEW_NOT_EXISTED(1001, "Review not existed",HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(1002, "Role not existed" ,HttpStatus.BAD_REQUEST );
    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
// sai valudision key 1001
// đã tồn tại 1001
//chưa tồn tại 1002
// lỗi chưa đuunsg đủ ký tự lỗi 1003
