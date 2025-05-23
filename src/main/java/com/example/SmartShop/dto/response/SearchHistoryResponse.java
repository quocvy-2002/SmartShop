package com.example.SmartShop.dto.response;

import com.example.SmartShop.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchHistoryResponse {
    Integer searchId;
    String query;
    LocalDate createdAt;
    String processedQuery;
}
