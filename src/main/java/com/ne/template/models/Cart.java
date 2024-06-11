package com.ne.template.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToOne
    @JoinColumn(name = "userId",nullable = false)
    private User user;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    private Set<CartItem> cartItems = new HashSet<>();

}
