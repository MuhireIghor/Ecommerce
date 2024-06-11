package com.ne.template.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderItemDto {
    private String productName;
    private Integer quantity;
    private Double price;
    private UUID orderId;
}
