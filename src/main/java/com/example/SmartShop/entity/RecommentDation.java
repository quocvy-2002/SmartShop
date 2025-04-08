package com.example.SmartShop.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "RECOMMENDATION")
public class RecommentDation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendation_id")
    Integer recommendationId;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "user_id" , nullable = false)
    User user;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @Column(name = "score")
    BigDecimal score;

    @Column(name = "created_at")
    LocalDate created_at;
}
