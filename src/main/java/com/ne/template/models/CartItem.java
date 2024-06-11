package com.ne.template.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class CartItem {
    @Id
    @GeneratedValue
    private UUID ID;
    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)

    private Product product;
    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    private Integer quantity;


}
