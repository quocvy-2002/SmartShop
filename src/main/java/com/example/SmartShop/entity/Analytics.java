package com.example.SmartShop.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "ANALYTICS")
public class Analytics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "analytics_id")
    Integer analyticsId;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id",nullable = false )
    User user;

    @OneToOne
    @JoinColumn(name = "store_id",referencedColumnName = "store_id",nullable = false )
    Store store;

    @Column(name = "type")
    String type;

    @Column(name = "value")
    String value;

    @Column(name = "created_at")
    LocalDate createdAt;





}
