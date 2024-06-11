package com.ne.template.controllers;

import com.ne.template.dtos.requests.CreateCartDto;
import com.ne.template.models.Cart;
import com.ne.template.services.ICartService;
import com.ne.template.utils.ApiResponse;
import com.ne.template.utils.ExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    private final ICartService cartService;

    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/create-user-cart")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
    public ResponseEntity<ApiResponse> createCart() {
        try {
            return ResponseEntity.ok(
                    new ApiResponse(
                            true,
                            "Cart created successfully",
                            cartService.createCart()
                    )
            );

        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }

    }

    @PostMapping("/add-to-cart/{prodName}")
    public ResponseEntity<ApiResponse> addToCart(@PathVariable String prodName, @RequestBody CreateCartDto createCartDto) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse(
                            true,
                            "Product added to cart successfully",
                            cartService.addToCart(prodName, createCartDto.getQuantity())
                    )
            );
        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }


    @GetMapping("/by-logged-in-user")

    public ResponseEntity<ApiResponse> getCart() {
        try {
            return ResponseEntity.ok(
                    new ApiResponse(
                            true,
                            "Cart retrieved successfully",
                            cartService.getCartByLoggedInUser()
                    )
            );


        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
}
