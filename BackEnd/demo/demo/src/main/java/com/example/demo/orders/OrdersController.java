package com.example.demo.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    private final OrdersService ordersService;

    @Autowired
    public OrdersController(OrdersService ordersService){
        this.ordersService = ordersService;
    }

    @PostMapping
    public ResponseEntity<Orders> createOrder(@RequestBody OrdersRequest ordersRequest) {

        Orders order = new Orders();
        order.setCustomerId(ordersRequest.getCustomerId());
        order.setRestaurantId(ordersRequest.getRestaurantId());
        order.setStatus(ordersRequest.getStatus());
        order.setCreatedAt(LocalDateTime.now());
        order.setAcceptedAt(LocalDateTime.now());
        order.setCompletedAt(LocalDateTime.now());

        Orders createdOrder = ordersService.createOrder(order);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping
    public ResponseEntity<List<Orders>> getAllOrders() {
        List<Orders> orders = ordersService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long orderId) {
        Orders order = ordersService.getOrderById(orderId);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Orders> updateOrder(@PathVariable Long orderId, @RequestBody Orders updatedOrder) {
        Orders order = ordersService.updateOrder(orderId, updatedOrder);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("order/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        boolean deleted = ordersService.deleteOrder(orderId);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{orderId}/totalPrice")
    public ResponseEntity<Double> getOrderTotalPrice(@PathVariable Long orderId) {
        Orders order = ordersService.getOrderById(orderId);
        if (order != null) {
            double totalPrice = ordersService.calculateTotalPrice(order);
            return ResponseEntity.ok(totalPrice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
