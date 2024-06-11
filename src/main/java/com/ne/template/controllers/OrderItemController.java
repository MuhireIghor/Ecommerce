package com.ne.template.controllers;

import com.ne.template.dtos.requests.CreateOrderItemDto;
import com.ne.template.models.OrderItem;
import com.ne.template.services.IOrderItemService;
import com.ne.template.utils.ApiResponse;
import com.ne.template.utils.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order-item")

public class OrderItemController {
    private final IOrderItemService orderItemService;

    @Autowired
    public OrderItemController(IOrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping("/create-order-item")
    public ResponseEntity<ApiResponse> createOrderItem(@RequestBody CreateOrderItemDto orderItem) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse(
                            true,
                            "Order item added successfully",
                            orderItemService.createOrderItem(orderItem)
                    )
            );
        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }

    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllOrderItems() {
        try {
            return ResponseEntity.ok(
                    new ApiResponse(
                            true,
                            "All order items succcessfully fetched",
                            orderItemService.getOrderItems()
                    )
            );

        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }

    @DeleteMapping("/remove-item/{orderItemId}")
    public ResponseEntity<ApiResponse> removeOrderItem(@PathVariable(value = "orderItemId") UUID orderItemId) {
        try {
            orderItemService.deleteOrderItem(orderItemId);
            return ResponseEntity.ok(
                    new ApiResponse(
                            true,
                            "Order item removed successfully"
                    )
            );
        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
//    @GetMapping
//    public ResponseEntity<ApiResponse> getOrderItemsByOrderId(@RequestParam("orderId") Long orderId) {}
}
