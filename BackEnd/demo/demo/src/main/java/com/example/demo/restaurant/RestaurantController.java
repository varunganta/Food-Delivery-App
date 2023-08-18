package com.example.demo.restaurant;

import com.example.demo.registration.RegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/restaurants")
@CrossOrigin("http://localhost:3000")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService){
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable("id") Long restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        if (restaurant != null) {
            return ResponseEntity.ok(restaurant);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/restaurant/by-app-user/{appUserId}")
    public ResponseEntity<Long> getRestaurantIdByAppUserId(@PathVariable("appUserId") int appUserId) {
        Long restaurantId = restaurantService.getRestaurantIdByAppUserId( appUserId);
        if (restaurantId != null) {
            return ResponseEntity.ok(restaurantId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/restaurant/update/{id}")
    public ResponseEntity<Restaurant> updateRestaurantById(@PathVariable Long id, @RequestBody RestaurantRequest restaurantRequest) {
        Restaurant updatedRestaurant = restaurantService.updateRestaurantById(id, restaurantRequest);
        if (updatedRestaurant != null) {
            return ResponseEntity.ok(updatedRestaurant);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/restaurant/{id}")
    public ResponseEntity<Void> deleteRestaurantById(@PathVariable("id") Long restaurantId) {
        System.out.println("here");
        boolean isDeleted = restaurantService.deleteRestaurantById(restaurantId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
