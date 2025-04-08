package com.example.SmartShop.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "WISHLIST")
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_id")
    Integer wishlistId;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id",nullable = false )
    User user;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "product_id",referencedColumnName = "product_id" , nullable = false)
    Product product;

    @Column(name = "created_at")
    String created_at;
}
