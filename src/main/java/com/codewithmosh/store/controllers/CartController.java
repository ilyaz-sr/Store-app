package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.*;
import com.codewithmosh.store.exception.CartNotFoundException;
import com.codewithmosh.store.exception.ProductNotFoundException;
import com.codewithmosh.store.services.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
@Tag(name = "Carts")
public class CartController {
    private final CartService cartService;
    private static final Logger logger = LogManager.getLogger(CartController.class);


    @PostMapping("/createcart")
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder) {
        CartDto cartDto = cartService.createCart();
        URI uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/addtocart")
    public ResponseEntity<CartItemDto> addToCart(@RequestBody AddToCartRequest request) {
        CartItemDto cartItemDto = cartService.addToCart(request.getCartId(), request.getProductId(), request.getQuantity());

        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);

    }

    @PostMapping("/getcartbyid")
    public CartDto getCart(@RequestBody UUID cartId) {
        logger.trace("Getting cart by id {}", cartId);
        return cartService.getCart(cartId);
    }

    @PostMapping("/updateitem")
    public CartItemDto updateItem(@Valid @RequestBody UpdateCartItemRequest request) {

        return cartService.updateItem(request.getCartId(), request.getProductId(), request.getQuantity());
    }

    @PostMapping("/removeitem")
    public ResponseEntity<?> removeItem(@RequestBody CartandProductRequest request) {
        cartService.removeItem(request.getCartId(), request.getProductId());

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/clearcart")
    public ResponseEntity<Void> clearCart(@RequestBody UUID cartId) {
        cartService.clearCart(cartId);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCartNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Cart not found."));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFound() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Product not found."));
    }

}