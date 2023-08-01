package com.example.demo.orders;

import com.example.demo.appuser.AppUser;
import com.example.demo.restaurant.Restaurant;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrdersRequest {
    private Long customerId;
    private Long restaurantId;
    private String status;
}
