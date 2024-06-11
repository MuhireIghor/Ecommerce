package com.ne.template.repositories;

import com.ne.template.models.Cart;
import com.ne.template.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ICartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByUser(User user);

}
