package com.codewithmosh.store.mappers;

import com.codewithmosh.store.dtos.CartItemDto;
import com.codewithmosh.store.entities.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItemDto toCartItemDto(CartItem cartItem);

    CartItem fromCartDto(CartItemDto cartItemDto);
}
