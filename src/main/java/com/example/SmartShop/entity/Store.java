package com.example.SmartShop.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "STORE")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="store_id")
    Integer storeId;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id",nullable = false )
    User user;

    @Column(name = "name" , nullable = false)
    String name;

    @Column(name  = "description")
    String description;

    @Column(name  = "status")
    String status;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @OneToMany(mappedBy = "store",cascade = CascadeType.ALL)
    List<Product> products;

    @OneToOne(mappedBy = "store",cascade = CascadeType.ALL)
    Analytics analytics;

    @OneToMany(mappedBy = "store",cascade = CascadeType.ALL)
    List<SellerRecommentDation> sellerRecommentDations;
}
