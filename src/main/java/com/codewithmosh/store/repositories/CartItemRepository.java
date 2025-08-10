package com.codewithmosh.store.repositories;

import com.codewithmosh.store.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem save(CartItem newItem);
}
