package com.example.SmartShop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "USER")
public class    User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Integer userId;

    @Column(name = "user_name", nullable = false,unique = true)
    String userName;

    @Column(name = "password",nullable = false)
    String password;

    @Column(name = "email",nullable = false,unique = true)
    String email;

    @Column(name = "gender",nullable = false)
    String gender;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Column(name = "number_phone",unique = true)
    Integer numberPhone;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "role_id" , nullable = false)
    @JsonIgnore
    Role role;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    List<Order> orders;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    Store store;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    Cart cart;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    List<Review> reviews;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    List<Notification> notifications;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    List<SeachHistory> seachHistories;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    List<RecommentDation> recommentDations;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    Analytics analytics;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    List<Address> addresses;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    WishList wishList;
}
