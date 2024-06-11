package com.ne.template.services;

import com.ne.template.dtos.requests.CreateCartDto;
import com.ne.template.models.Cart;

import java.util.UUID;

public interface ICartService {
    Cart getCardById(UUID id);
    Cart getCartByLoggedInUser();
//    Cart getCartByUserIdOrCartId(UUID userId, UUID cartId);
    Cart addToCart( String prodName, Integer quantity);
    void clearCart(UUID cartId);
    Cart createCart() throws Exception;


}
