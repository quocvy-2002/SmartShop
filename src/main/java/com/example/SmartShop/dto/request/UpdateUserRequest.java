package com.example.SmartShop.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequest {
    @Size(min = 3,message = "INVALID_USERNAME")
    String userName;
    @Email(message = "INVALID_EMAIL")
    String email;
    String gender;
    Integer numberPhone;
    LocalDateTime updatedAt;
}
