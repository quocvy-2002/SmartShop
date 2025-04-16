package com.example.SmartShop.enums;

import lombok.Getter;

@Getter
public enum Type {
    SYSTEM("Thông báo hệ thống"),
    CUSTOMER("Thông báo khách hàng");

    private String description;

    Type(String description) {
        this.description = description;
    }
}
