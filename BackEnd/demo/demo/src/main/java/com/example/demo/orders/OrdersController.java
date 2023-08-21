package com.example.demo.orders;

import com.example.demo.OrderItem.OrderItem;
import com.example.demo.OrderItem.OrderItemRequest;
import com.example.demo.OrderItem.OrderItemService;
import com.example.demo.menu.Menu;
import com.example.demo.menu.MenuRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin("http://localhost:3000")
public class OrdersController {
    private final OrdersService ordersService;
    private final OrderItemService orderItemService;
    private final MenuRepository menuRepository;

    @Autowired
    public OrdersController(OrdersService ordersService, OrderItemService orderItemService, MenuRepository menuRepository){
        this.ordersService = ordersService;
        this.orderItemService = orderItemService;
        this.menuRepository = menuRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<Orders> createOrder(@RequestBody OrdersRequest ordersRequest) {
        Orders createdOrder = ordersService.createOrder(ordersRequest);

        if (createdOrder != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Orders>> getAllOrders() {
        List<Orders> orders = ordersService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/by-restaurant/{restaurantId}")
    public ResponseEntity<?> getOrdersByRestaurantId(@PathVariable Long restaurantId) {
        List<Orders> orders = ordersService.getOrdersByRestaurantId(restaurantId);
        if (orders.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/by-customer/{customerId}")
    public ResponseEntity<?> getOrdersByCustomerId(@PathVariable Long customerId) {
        List<Orders> orders = ordersService.getOrdersByCustomerId(customerId);
        if (orders.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
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


    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        boolean deleted = ordersService.deleteOrder(orderId);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{orderId}/totalPrice")
    public ResponseEntity<Long> getOrderTotalPrice(@PathVariable Long orderId) {
        Orders order = ordersService.getOrderById(orderId);
        if (order != null) {
            Long totalPrice = ordersService.calculateTotalPrice(orderId);
            return ResponseEntity.ok(totalPrice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
