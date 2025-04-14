package com.example.SmartShop.enums;

import lombok.Getter;

@Getter
public enum Status {
    PENDING("Please wait while the system processes your request."),
    APPROVED("Your request has been approved."),
    REJECTED("Your request has been rejected."),
    INACTIVE("Store item is currently inactive.");
    private final String message;
    Status(String message) {
        this.message = message;
    }
}

