package com.example.SmartShop.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WishListResponse {
    private Integer wishlistId;
    private String userName;
    private Integer productId;
    private String productName;
    private String createdAt;
}
