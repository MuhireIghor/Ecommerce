package com.ne.template.serviceImpls;

import com.ne.template.dtos.requests.CreateOrderDto;
import com.ne.template.dtos.requests.UpdateOrderDto;
import com.ne.template.enums.EOrderStatus;
import com.ne.template.exceptions.BadRequestAlertException;
import com.ne.template.exceptions.NotFoundException;
import com.ne.template.models.*;
import com.ne.template.repositories.IOrderItem;
import com.ne.template.repositories.IOrderRepository;
import com.ne.template.repositories.IProductRepository;
import com.ne.template.services.ICartService;
import com.ne.template.services.IOrderService;
import com.ne.template.services.IUserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service

public class OrderServiceImpl implements IOrderService {
    private final IOrderRepository orderRepository;
    private final IUserService userService;
    private final ICartService cartService;
    private final IOrderItem orderItemRepository;
    private final IProductRepository productRepository;


    @Autowired
    public OrderServiceImpl(IOrderRepository orderRepository, IUserService userService, ICartService cartService, IOrderItem orderItem, IProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.cartService = cartService;
        this.orderItemRepository = orderItem;
        this.productRepository = productRepository;

    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(UUID id) {
        boolean exists = orderRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException(String.format("Order with id %s is not found", id));
        }
        return orderRepository.findById(id).get();
    }

    @Override
    @Transactional
//    public Order createOrder(CreateOrderDto dto) {
//        try {
//            User user = userService.getLoggedInUser();
//            Cart cart = cartService.getCartByLoggedInUser();
//            Set<CartItem> cartItems = cart.getCartItems();
//            if (cartItems.isEmpty()) {
//                throw new BadRequestAlertException("Cannot place order with an empty cart");
//            }
//            Order newOrder = new Order();
//            newOrder.setOrderDate(dto.getOrderDate());
//
//            newOrder.setStatus(dto.getStatus());
//            newOrder.setUser(user);
//            newOrder.setCart(cart);
//            Order savedOrder = orderRepository.save(newOrder);
//            Double totalQuantity =0.0;
////
//            for (CartItem cartItem : cartItems) {
//                Product product = cartItem.getProduct();
//                int quantityOrdered = cartItem.getQuantity();
//
//
//                // Check if there is enough stock
//                if (product.getStockQuantity() < quantityOrdered) {
//                    throw new BadRequestAlertException("Not enough stock available for product: " + product.getProductName());
//                }
//
//                // Reduce the stock quantity
//                product.setStockQuantity(product.getStockQuantity() - quantityOrdered);
//                productRepository.save(product);
//
//                // Create order item
//                OrderItem orderItem = new OrderItem();
//                orderItem.setOrder(newOrder);
//                orderItem.setProduct(product);
//                orderItem.setQuantity(quantityOrdered);
//                orderItem.setPrice(product.getPrice());
//                totalQuantity+=quantityOrdered*product.getStockQuantity();
//                savedOrder.getOrderItems().add(orderItem);
//
//                orderItemRepository.save(orderItem);
//            }
//            savedOrder.setTotalAmount(totalQuantity);
//
//            orderRepository.save(savedOrder);
//            cartService.clearCart(cart.getId());
//            return savedOrder;
//        } catch (Exception e) {
//            throw new BadRequestAlertException(e.getMessage());
//        }
//    }
    public Order createOrder(CreateOrderDto dto) {
        try {
            User user = userService.getLoggedInUser();
            Cart cart = cartService.getCartByLoggedInUser();
            Set<CartItem> cartItems = cart.getCartItems();

            if (cartItems.isEmpty()) {
                throw new BadRequestAlertException("Cannot place order with an empty cart");
            }

            Order newOrder = new Order();
            newOrder.setOrderDate(dto.getOrderDate());
//            newOrder.setTotalAmount(dto.getTotalAmount());
            newOrder.setStatus(dto.getStatus());
            newOrder.setUser(user);

            // Save the order first to generate its ID
            Order savedOrder = orderRepository.save(newOrder);
            Double totalPrice = 0.0;
            for (CartItem cartItem : cartItems) {
                Product product = cartItem.getProduct();
                int quantityOrdered = cartItem.getQuantity();

                // Check if there is enough stock
                if (product.getStockQuantity() < quantityOrdered) {
                    throw new BadRequestAlertException("Not enough stock available for product: " + product.getProductName());
                }

                // Reduce the stock quantity
                product.setStockQuantity(product.getStockQuantity() - quantityOrdered);
                productRepository.save(product);

                // Create order item
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(savedOrder); // Set the saved order reference
                orderItem.setProduct(product);
                orderItem.setQuantity(quantityOrdered);
                orderItem.setPrice(product.getPrice());
                totalPrice+=quantityOrdered*product.getStockQuantity();

                savedOrder.getOrderItems().add(orderItem);

                orderItemRepository.save(orderItem);
            }
            savedOrder.setTotalAmount(totalPrice);
            // Save the order again with order items
            orderRepository.save(savedOrder);

            // Clear the cart after order is placed
            cartService.clearCart(cart.getId());

            return savedOrder;
        } catch (Exception e) {
            throw new BadRequestAlertException(e.getMessage());
        }
    }

    @Override
    public Order updateOrder(UpdateOrderDto dto, UUID id) {
        boolean exists = orderExists(id);
        if (!exists) {
            throw new NotFoundException(String.format("Order with id %s is not found", id));
        }
        Order order = orderRepository.findById(id).get();
        order.setOrderDate(dto.getOrderDate());
        order.setTotalAmount(dto.getTotalAmount());
        order.setStatus(dto.getStatus());
        return orderRepository.save(order);

    }

    @Override
    public void deleteOrder(UUID id) {
        boolean isExistent = orderExists(id);
        if (!isExistent) {
            throw new NotFoundException(String.format("Order with id %s is not found", id));
        }
        orderRepository.deleteById(id);
    }

    @Override
    public boolean orderExists(UUID id) {
        return orderRepository.existsById(id);
    }

    @Override
    public Order cancelOrder(UUID id) {
        boolean isOrderExistant = orderExists(id);
        if (!isOrderExistant) {
            throw new NotFoundException(String.format("Order with id %s is not found", id));
        }
        Order order = orderRepository.findById(id).get();
        order.setStatus(EOrderStatus.CANCELLED);
        return orderRepository.save(order);
    }
}
