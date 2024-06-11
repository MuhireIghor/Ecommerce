package com.ne.template.repositories;

import com.ne.template.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ICartItemRepository extends JpaRepository<CartItem, UUID> {
}
