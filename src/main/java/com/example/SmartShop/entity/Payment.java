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
@Table(name = "PAYMENT")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="payment_id")
    Integer paymentId;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "order_id" , nullable = false)
    Order order;

    @Column(name = "method")
    String method;

    @Column(name = "amount")
    BigDecimal amount;

    @Column(name = "status")
    String status;

    @Column(name = "created_at")
    LocalDate createdAt;
}
