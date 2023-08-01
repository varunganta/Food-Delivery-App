package com.example.demo.restaurant;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RestaurantRequest {
    private final String restaurantName;
    private final String restaurantLocation;
}

