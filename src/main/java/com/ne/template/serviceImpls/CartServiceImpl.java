package com.ne.template.serviceImpls;


import com.ne.template.dtos.requests.CreateCartDto;
import com.ne.template.exceptions.BadRequestAlertException;
import com.ne.template.exceptions.NotFoundException;
import com.ne.template.models.Cart;
import com.ne.template.models.CartItem;
import com.ne.template.models.Product;
import com.ne.template.models.User;
import com.ne.template.repositories.ICartItemRepository;
import com.ne.template.repositories.ICartRepository;
import com.ne.template.services.ICartService;
import com.ne.template.services.IProductService;
import com.ne.template.services.IUserService;
import com.ne.template.utils.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CartServiceImpl implements ICartService {
    private final ICartRepository cartRepository;
    private final IProductService productService;
    private final ICartItemRepository cartItemRepository;
    private final IUserService userService;

    @Autowired
    public CartServiceImpl(ICartRepository cartRepository, IProductService productService, ICartItemRepository cartItemRepository, IUserService userService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;

    }

    @Override
    public Cart getCardById(UUID id) {
        return cartRepository.findById(id).orElseThrow(() -> new NotFoundException("Cart not found"));
    }

    @Override
    public Cart getCartByLoggedInUser() {
        try {
            User user = userService.getLoggedInUser();
            if(user == null) {
                throw new BadRequestAlertException("User not logged in");
            }


            return cartRepository.findByUser(user).orElseThrow(() -> new NotFoundException("Cart associatd with userId " + user.getId() + " is not found"));
        } catch (Exception e) {
            throw new BadRequestAlertException(e.getMessage());
        }


    }


    @Override
    public Cart addToCart(String productName, Integer quantity) {
        try {

            Cart cart = getCartByLoggedInUser();

            if (cart == null) {
                throw new NotFoundException(String.format("Cart for user with id %s is not found", userService.getLoggedInUser().getId()));
            }
            Product productToAdd = productService.getProductByName(productName);
            if (productToAdd.getStockQuantity() < quantity) {
                throw new BadRequestAlertException(String.format("Product with id %s is out of stock", productToAdd.getId()));
            }
            Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productToAdd.getId()))
                    .findFirst();
            if (existingCartItem.isPresent()) {
                CartItem cartItem = existingCartItem.get();
                int newQuantity = cartItem.getQuantity() + quantity;
                if (productToAdd.getStockQuantity() < newQuantity) {
                    throw new BadRequestAlertException("Not enough stock available for product: " + productToAdd.getProductName() + "only left with " + productToAdd.getStockQuantity() + productToAdd.getProductName() + "only left with in stock ");
                }
                cartItem.setQuantity(newQuantity);
                cartItemRepository.save(cartItem);


            } else {
                CartItem cartItem = new CartItem();
                cartItem.setProduct(productToAdd);
                cartItem.setQuantity(quantity);
                cart.getCartItems().add(cartItem);
                cartItemRepository.save(cartItem);
            }

            return cartRepository.save(cart);

        } catch (Exception e) {
            throw new BadRequestAlertException("Invalid user logged in");

        }

    }

    @Override
    public void clearCart(UUID cartId) {
        Cart cardToClear = cartRepository.findById(cartId).orElseThrow(() -> new NotFoundException("Cart not found"));
        cardToClear.getCartItems().clear();
        cartRepository.save(cardToClear);

    }

    @Override
    public Cart createCart() throws Exception{


        User user = userService.getLoggedInUser();
        if (cartRepository.findByUser(user).isPresent()) {
            throw new BadRequestAlertException("Cart already exists");
        }
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);


    }

}
