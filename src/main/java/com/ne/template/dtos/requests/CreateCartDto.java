package com.ne.template.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCartDto {
    private UUID userId;
    private Integer quantity;

}
