package com.codewithmosh.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class AddToCartRequest {
    private UUID cartId;
    private Long productId;
    private Integer quantity;

}
