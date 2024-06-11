package com.ne.template.services;

import com.ne.template.dtos.requests.CreateOrderDto;
import com.ne.template.dtos.requests.UpdateOrderDto;
import com.ne.template.models.Order;

import java.util.List;
import java.util.UUID;

public interface IOrderService {
    List<Order> getOrders();
    Order getOrderById(UUID id);
    Order createOrder(CreateOrderDto dto);
    Order updateOrder(UpdateOrderDto dto, UUID id);
    void deleteOrder(UUID id);
    boolean orderExists(UUID id);
    public Order cancelOrder(UUID id);



}
