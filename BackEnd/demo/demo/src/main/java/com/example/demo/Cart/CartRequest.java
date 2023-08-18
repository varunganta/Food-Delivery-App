package com.example.demo.Cart;

import com.example.demo.appuser.AppUser;
import lombok.*;

import com.example.demo.menu.Menu;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CartRequest {
    private Long itemId;
    private Long appUserId;
    private int quantity;
    private Long restaurantId;
}
