package com.example.SmartShop.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "PRODUCT")
public class SellerRecommentDation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_rec_id")
    Integer sellerRecId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id",referencedColumnName = "store_id")
    Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",referencedColumnName = "product_id")
    Product product;


    @Column(name = "suggestion_type")
    String suggestionType;

    @Column(name = "score")
    BigDecimal score;

    @Column(name = "created_at")
    LocalDate  createdAt;


}
