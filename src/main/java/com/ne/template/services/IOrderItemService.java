package com.ne.template.services;

import com.ne.template.dtos.requests.CreateOrderItemDto;
import com.ne.template.models.OrderItem;

import java.util.List;
import java.util.UUID;

public interface IOrderItemService {
    OrderItem getOrderItem(UUID id);
    List<OrderItem> getOrderItems();
    OrderItem createOrderItem(CreateOrderItemDto orderItem);
    void deleteOrderItem(UUID id);
    OrderItem updateOrderItem(OrderItem orderItem,UUID orderItemId);
    boolean isOrderItemExistent(UUID id);

}
