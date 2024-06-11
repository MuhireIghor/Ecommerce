package com.ne.template.controllers;

import com.ne.template.dtos.requests.CreateOrderDto;
import com.ne.template.dtos.requests.UpdateOrderDto;
import com.ne.template.services.IOrderService;
import com.ne.template.utils.ApiResponse;
import com.ne.template.utils.ExceptionUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final IOrderService orderService;

    @Autowired
    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create-order")
    public ResponseEntity<ApiResponse> createOrder(@RequestBody @Valid CreateOrderDto dto) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse(
                            true,
                            "Order created Successfully",
                            orderService.createOrder(dto)
                    )
            );

        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }

    @GetMapping("/all")

    public ResponseEntity<ApiResponse> getAllOrders() {
        try {
            return ResponseEntity.ok(
                    new ApiResponse(
                            true,
                            "Orders retrieved successfully",
                            orderService.getOrders()
                    )
            );
        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable(value = "orderId") UUID id) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse(
                            true,
                            "Order retrieved successfully",
                            orderService.getOrderById(id)
                    )
            );
        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }

    }

    @PutMapping("/update/{orderId}")
    public ResponseEntity<ApiResponse> updateOrder(@PathVariable(value = "orderId") UUID id, UpdateOrderDto dto) {
        try {

            return ResponseEntity.ok(
                    new ApiResponse(
                            true,
                            "Order updated successfully",
                            orderService.updateOrder(dto, id)
                    )
            );

        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<ApiResponse> deleteOrder(@PathVariable(value = "orderId") UUID orderId) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse(
                            true,
                            "Order deleted successfuly"
                    )
            );

        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }

    @PutMapping("/cancel-order/{orderId}")
    public ResponseEntity<ApiResponse> cancelOrder(@PathVariable(value = "orderId") UUID orderId) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse(
                            true,
                            "Order with id " + orderId + " cancelled successfully",
                            orderService.cancelOrder(orderId)

                    )
            );
        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }

    }

    @PostMapping("/place-order")
    public ResponseEntity<ApiResponse> placeOrder(CreateOrderDto dto) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse(
                            true,
                            "Order placed successfully ",
                            orderService.createOrder(dto)
                    )
            );

        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }


}
