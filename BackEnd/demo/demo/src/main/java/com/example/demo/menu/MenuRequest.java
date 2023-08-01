package com.example.demo.menu;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class MenuRequest {
    private final String itemName;
    private final Long itemPrice;
    private Long restaurantId;
}
