package com.ne.template.models;

import com.ne.template.dtos.requests.CreateProductDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private UUID id;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_description")
    private String productDescription;
    private Double price;
    private Integer stockQuantity;
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(fetch = FetchType.LAZY,orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_item_id")
    private Set<OrderItem> orderItems = new HashSet<>();

    public Product(String productName, String productDescription,Double price,Integer stockQuantity,Category category) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }


}
