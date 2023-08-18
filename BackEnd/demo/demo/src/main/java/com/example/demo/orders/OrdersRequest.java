package com.example.demo.orders;

import com.example.demo.OrderItem.OrderItemRequest;
import com.example.demo.appuser.AppUser;
import com.example.demo.restaurant.Restaurant;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrdersRequest {
    private Long customerId;
    private Long restaurantId;
    //private Long itemId;
    private String status;
    public List<OrderItemRequest> orderItems;
    private Long totalPrice;

}
