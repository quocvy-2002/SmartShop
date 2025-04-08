package com.example.SmartShop.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "PRODUCT_GROUP")
public class ProductGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="product_group_id")
    Integer productGroupId;

    @Column(name = "product_group_name", nullable = false ,unique = true)
    String productGroupName;

    @Column(name = "description")
    String description;

    @OneToMany(mappedBy = "productGroup",cascade = CascadeType.ALL)
    List<Product> products;

    @OneToMany(mappedBy = "productGroup",cascade = CascadeType.ALL)
    List<CategoryTrend> categoryTrends;
}
