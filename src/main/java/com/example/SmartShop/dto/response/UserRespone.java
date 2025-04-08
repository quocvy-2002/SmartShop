package com.example.SmartShop.dto.response;

import com.example.SmartShop.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRespone {
    String userName;
    String email;
    String gender;
    Integer numberPhone;
    Role role;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
