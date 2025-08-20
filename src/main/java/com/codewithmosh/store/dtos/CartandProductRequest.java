package com.codewithmosh.store.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class CartandProductRequest {
    private UUID cartId;
    private Long productId;

}
