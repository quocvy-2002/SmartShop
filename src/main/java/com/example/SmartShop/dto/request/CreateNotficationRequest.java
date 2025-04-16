package com.example.SmartShop.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE )
public class CreateNotficationRequest {
    String type;
    String message;
}
