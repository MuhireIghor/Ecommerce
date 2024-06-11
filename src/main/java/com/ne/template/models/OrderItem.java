package com.ne.template.models;

import com.ne.template.audits.TimestampAudit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends TimestampAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private Integer quantity;
    private Double price;
    @ManyToOne

    private Order order;
}
