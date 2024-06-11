package com.ne.template.serviceImpls;


import com.ne.template.dtos.requests.CreateOrderItemDto;
import com.ne.template.exceptions.NotFoundException;
import com.ne.template.models.OrderItem;
import com.ne.template.models.Product;
import com.ne.template.repositories.IOrderItem;

import com.ne.template.services.IOrderItemService;

import com.ne.template.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderItemServiceImpl implements IOrderItemService {
    private final IOrderItem orderItemRepository;
    private final IProductService productService;

    @Autowired
    public OrderItemServiceImpl(IOrderItem orderRepository, IProductService productService) {
        this.orderItemRepository = orderRepository;
        this.productService = productService;
    }

    @Override
    public OrderItem getOrderItem(UUID id) {
        return orderItemRepository.findById(id).orElse(null);
    }

    @Override
    public List<OrderItem> getOrderItems() {
        return orderItemRepository.findAll();
    }

    @Override
    public OrderItem createOrderItem(CreateOrderItemDto orderItem) {
        Product orderItemProduct = productService.getProductByName(orderItem.getProductName());
        if (orderItemProduct == null) {
            throw new NotFoundException(String.format("Product with id %s is not found!", orderItem.getProductName()));

        }
        OrderItem newOrderItem = new OrderItem();
        newOrderItem.setProduct(orderItemProduct);
        newOrderItem.setQuantity(orderItem.getQuantity());
        newOrderItem.setPrice(orderItem.getPrice());
        return orderItemRepository.save(newOrderItem);


    }

    @Override
    public void deleteOrderItem(UUID id) {
        boolean isOrderItemExists = orderItemRepository.existsById(id);
        if (!isOrderItemExists) {
            throw new NotFoundException(String.format("OrderItem with id %s is not found!", id));
        }
        orderItemRepository.deleteById(id);


    }

    @Override
    public OrderItem updateOrderItem(OrderItem orderItem,UUID id) {
      boolean isOrderItemExists = orderItemRepository.existsById(id);
      if (!isOrderItemExists) {
          throw new NotFoundException(String.format("OrderItem with id %s is not found!", id));
      }
      OrderItem oldOrderItem = orderItemRepository.findById(id).get();
      oldOrderItem.setQuantity(orderItem.getQuantity());
      oldOrderItem.setPrice(orderItem.getPrice());
      return orderItemRepository.save(oldOrderItem);
    }

    @Override
    public boolean isOrderItemExistent(UUID id) {
        return orderItemRepository.existsById(id);
    }
}
