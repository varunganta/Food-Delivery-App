package com.example.demo.orders;

import com.example.demo.OrderItem.OrderItem;
import com.example.demo.OrderItem.OrderItemRepository;
import com.example.demo.appuser.AppUser;
import com.example.demo.menu.Menu;
import com.example.demo.menu.MenuRepository;
import com.example.demo.restaurant.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Order;
import java.util.List;

@Service
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final MenuRepository menuRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrdersService(OrdersRepository ordersRepository, MenuRepository menuRepository, OrderItemRepository orderItemRepository){
        this.ordersRepository = ordersRepository;
        this.menuRepository = menuRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public Orders createOrder(Orders order) {
        return ordersRepository.save(order);
    }

    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }

    public Orders getOrderById(Long orderId) {
        return ordersRepository.findById(orderId).orElse(null);
    }

    public Orders updateOrder(Long orderId, Orders updatedOrder) {
        Orders existingOrder = getOrderById(orderId);
        if (existingOrder == null) {
            return null;
        }

        existingOrder.setStatus(updatedOrder.getStatus());
        //existingOrder.setCreatedAt(updatedOrder.getCreatedAt());
        existingOrder.setAcceptedAt(updatedOrder.getAcceptedAt());
        existingOrder.setCompletedAt(updatedOrder.getCompletedAt());
        Long updatedCustomer = updatedOrder.getCustomerId();
        if (updatedCustomer != null) {
            existingOrder.setCustomerId(updatedCustomer);
        }

        Long updatedRestaurant = updatedOrder.getRestaurantId();
        if (updatedRestaurant != null) {
            existingOrder.setRestaurantId(updatedRestaurant);
        }

        return ordersRepository.save(existingOrder);
    }

    public boolean deleteOrder(Long orderId) {
        Orders order = getOrderById(orderId);
        if (order == null) {
            return false;
        }
        ordersRepository.delete(order);
        return true;
    }

//    public double calculateTotalPrice(Orders order){
//        double totalPrice = 0.0;
//        List<OrderItem> orderItems = order.getOrderItems();
//        for(OrderItem orderItem : orderItems){
//            Long menuId = orderItem.getMenuId();
//            Menu menu = menuRepository.findById(menuId).orElse(null);
//            if(menu != null){
//                double itemPrice = menu.getItemPrice();
//                totalPrice += itemPrice;
//            }
//        }
//        return totalPrice;
//    }
    public double calculateTotalPrice(Orders order) {
        double totalPrice = 0.0;
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getOrderId());
        for (OrderItem orderItem : orderItems) {
            Long menuId = orderItem.getMenuId();
            Menu menu = menuRepository.findById(menuId).orElse(null);
            if (menu != null) {
                double itemPrice = menu.getItemPrice();
                totalPrice += itemPrice;
            }
        }
        return totalPrice;
    }
}
