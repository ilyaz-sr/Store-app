package com.codewithmosh.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class CartItemDto {
    private Long id;
    private UUID cartId;
    private Long productId;
    private Integer quantity;
    private BigDecimal totalPrice;
}
