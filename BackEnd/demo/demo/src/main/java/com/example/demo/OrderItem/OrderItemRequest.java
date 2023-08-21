package com.example.demo.OrderItem;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OrderItemRequest {
    private Long orderId;
    private Long menuId;
    private double price;
    private Long quantity;
}
