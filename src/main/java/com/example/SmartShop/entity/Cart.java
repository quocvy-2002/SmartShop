package com.example.SmartShop.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "CART")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="cart_id")
    Integer cartId;

    @OneToOne()
    @JoinColumn(name = "user_id",referencedColumnName = "user_id",unique = true )
    User user;

    @OneToMany(mappedBy = "cart" ,cascade = CascadeType.ALL)
    List<CartItem> cartItems;

    @Column(name = "created_at")
    LocalDate createdAt;

    @Column(name = "updated_at")
    LocalDate updatedAt;

    @Column(name = "status")
    String status;
}
