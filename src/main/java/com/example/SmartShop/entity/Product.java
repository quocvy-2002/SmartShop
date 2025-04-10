package com.example.SmartShop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "PRODUCT")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    Integer productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "store_id", referencedColumnName = "store_id")
    Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "product_group_id", referencedColumnName = "product_group_id")
    ProductGroup productGroup;

    @Column(name = "product_name", nullable = false)
    String productName;

    @Column(name = "description")
    String description;

    @Column(name = "price", nullable = false)
    BigDecimal price;

    @Column(name = "stock")
    Integer stock;

    @Column(name = "status")
    String status;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "popular_id", referencedColumnName = "popular_id")
    PopularProduct popularProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_rec_id", referencedColumnName = "seller_rec_id")
    SellerRecommentDation sellerRecommendation;

    @Column(name = "calculated_at")
    LocalDate calculatedAt;

    @Column(name = "popularity_score")
    BigDecimal popularityScore;

    @Column(name = "score")
    BigDecimal score;

    @Column(name = "suggestion_type")
    String suggestionType;

    // Các mối quan hệ @OneToMany
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<CartItem> cartItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<Review> reviews;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<RecommentDation> recommendations;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<WishList> wishLists;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<SellerRecommentDation> sellerRecommendations;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<PopularProduct> popularProducts;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<ProductView> productView;
}