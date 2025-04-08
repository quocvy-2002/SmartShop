package com.example.SmartShop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "CATEGORY_TREND")
public class CategoryTrend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trend_id")
    Integer trendId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "product_group_id",referencedColumnName = "product_group_id")
    ProductGroup productGroup;

    @Column(name = "popularity_score")
    BigDecimal popularityScore;

    @Column(name = "calculated_at")
    LocalDate calculatedAt;

}
