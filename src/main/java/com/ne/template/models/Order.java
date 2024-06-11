package com.ne.template.models;

import com.ne.template.audits.TimestampAudit;
import com.ne.template.dtos.requests.CreateOrderDto;
import com.ne.template.enums.EOrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_table")
public class Order extends TimestampAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Date orderDate;
    @Column(name = "total_amount")
    private Double totalAmount = 0.0;
    @Enumerated(EnumType.STRING)
    private EOrderStatus status = EOrderStatus.PENDING;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_items_id")
    private Set<OrderItem> orderItems = new HashSet<>();
    public Order(CreateOrderDto dto,User user){
        this.orderDate = dto.getOrderDate();
        this.status = dto.getStatus();
        this.user = user;
    }


}
