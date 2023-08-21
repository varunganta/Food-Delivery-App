package com.example.demo.OrderItem;

import com.example.demo.menu.Menu;
import com.example.demo.menu.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderItems")
@CrossOrigin("http://localhost:3000")
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final MenuService menuService;

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemController(OrderItemService orderItemService, MenuService menuService, OrderItemRepository orderItemRepository) {
        this.orderItemService = orderItemService;
        this.menuService = menuService;
        this.orderItemRepository = orderItemRepository;
    }

    @PostMapping("/batch-create")
    public ResponseEntity<List<OrderItem>> createOrderItems(@RequestBody List<OrderItem> orderItems) {
        List<OrderItem> createdOrderItems = orderItemService.createOrderItems(orderItems);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderItems);
    }

    @GetMapping
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {
        List<OrderItem> orderItems = orderItemService.getAllOrderItems();
        return ResponseEntity.ok(orderItems);
    }

    @GetMapping("/{orderItemId}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long orderItemId) {
        OrderItem orderItem = orderItemService.getOrderItemById(orderItemId);
        if (orderItem == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderItem);
    }

    @GetMapping("/byOrder/{orderId}")
    public ResponseEntity<List<OrderItem>> getOrderItemsByOrderId(@PathVariable Long orderId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        if (orderItems.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderItems);
    }

    @PutMapping("/{orderItemId}")
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Long orderItemId, @RequestBody OrderItem updatedOrderItem) {
        OrderItem orderItem = orderItemService.updateOrderItem(orderItemId, updatedOrderItem);
        if (orderItem == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderItem);
    }

    @DeleteMapping("/{orderItemId}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long orderItemId) {
        boolean deleted = orderItemService.deleteOrderItem(orderItemId);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
