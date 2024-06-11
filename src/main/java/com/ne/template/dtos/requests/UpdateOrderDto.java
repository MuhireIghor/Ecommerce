package com.ne.template.dtos.requests;

import com.ne.template.enums.EOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderDto {
    private Date orderDate;
    private Double totalAmount;
    private EOrderStatus status;

}
