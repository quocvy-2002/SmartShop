package com.example.SmartShop.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PopularProductResponse {
    Integer popularId;
    Integer productId;
    String productName;
    Integer productGroupId;
    String productGroupName;
    BigDecimal popularityScore;
    LocalDate calculatedAt;

}
