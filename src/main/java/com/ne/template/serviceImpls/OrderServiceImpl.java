package com.ne.template.serviceImpls;

import com.ne.template.dtos.requests.CreateOrderDto;
import com.ne.template.dtos.requests.UpdateOrderDto;
import com.ne.template.enums.EOrderStatus;
import com.ne.template.exceptions.NotFoundException;
import com.ne.template.models.Order;
import com.ne.template.repositories.IOrderRepository;
import com.ne.template.services.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service

public class OrderServiceImpl implements IOrderService {
    private final IOrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
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
    public Order createOrder(CreateOrderDto dto) {
        Order newOrder = new Order();
        newOrder.setOrderDate(dto.getOrderDate());
        newOrder.setTotalAmount(dto.getTotalAmount());
        newOrder.setStatus(dto.getStatus());
        return orderRepository.save(newOrder);
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
        if(!isOrderExistant){
            throw new NotFoundException(String.format("Order with id %s is not found", id));
        }
        Order order = orderRepository.findById(id).get();
        order.setStatus(EOrderStatus.CANCELLED);
        return orderRepository.save(order);
    }
}
