package com.example.SmartShop.dto.request;

import com.example.SmartShop.entity.Role;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserRequest {
    @Size(min = 3,message = "INVALID_USERNAME")
    String userName;
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$",
            message = "INVALID_PASSWORD")
    String password;
    @Email(message = "INVALID_EMAIL")
    String email;
    String gender;
    Integer numberPhone;
    Role role;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
