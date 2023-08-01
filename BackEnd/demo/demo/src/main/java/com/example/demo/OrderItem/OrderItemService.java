package com.example.demo.OrderItem;

import com.example.demo.menu.Menu;
import com.example.demo.menu.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final MenuRepository menuRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository, MenuRepository menuRepository) {
        this.orderItemRepository = orderItemRepository;
        this.menuRepository = menuRepository;
    }

    public OrderItem createOrderItem(OrderItemRequest orderItemRequest) {
        Optional<Menu> menuOptional = menuRepository.findById(orderItemRequest.getMenuId());
        if (menuOptional.isPresent()) {
            Menu menu = menuOptional.get();

            OrderItem newOrderItem = new OrderItem();
            newOrderItem.setOrderId(orderItemRequest.getOrderId());
            newOrderItem.setMenuId(orderItemRequest.getMenuId());
            newOrderItem.setPrice(orderItemRequest.getPrice());

            return orderItemRepository.save(newOrderItem);
        } else {
            throw new IllegalArgumentException("Menu with ID " + orderItemRequest.getMenuId() + " not found");
        }
    }

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public OrderItem getOrderItemById(Long orderItemId) {
        return orderItemRepository.findById(orderItemId).orElse(null);
    }

    public OrderItem updateOrderItem(Long orderItemId, OrderItem updatedOrderItem) {
        OrderItem existingOrderItem = getOrderItemById(orderItemId);
        if (existingOrderItem == null) {
            return null;
        }

        // Update the fields from the updatedOrderItem entity
        existingOrderItem.setOrderId(updatedOrderItem.getOrderId());
        existingOrderItem.setMenuId(updatedOrderItem.getMenuId());
        existingOrderItem.setPrice(updatedOrderItem.getPrice());

        return orderItemRepository.save(existingOrderItem);
    }

    public boolean deleteOrderItem(Long orderItemId) {
        OrderItem orderItem = getOrderItemById(orderItemId);
        if (orderItem == null) {
            return false;
        }
        orderItemRepository.delete(orderItem);
        return true;
    }
}
