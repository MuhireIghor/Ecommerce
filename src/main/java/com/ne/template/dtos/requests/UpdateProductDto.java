package com.ne.template.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductDto {
    private String productName;
    private String productDescription;
    private Double price;
    private Integer stockQuantity;
    private String categoryName;
}
