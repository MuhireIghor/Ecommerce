package com.ne.template.models;

import com.ne.template.audits.TimestampAudit;
import com.ne.template.enums.EOrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="order_table")
public class Order extends TimestampAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Date orderDate;
    @Column(name = "total_amount")
    private Double totalAmount = 0.0;
    @Enumerated(EnumType.STRING)
    private EOrderStatus status = EOrderStatus.PENDING;



}
