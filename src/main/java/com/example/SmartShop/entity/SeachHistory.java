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
@Table(name = "SEARCHHISTORY")
public class SeachHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "search_id")
    Integer searchId;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "user_id" , nullable = false)
    User user;

    @Column(name = "query")
    String query;

    @Column(name = "created_at")
    LocalDate createdAt;

    @Column(name= "processed_query")
    String processedQuery;


}
