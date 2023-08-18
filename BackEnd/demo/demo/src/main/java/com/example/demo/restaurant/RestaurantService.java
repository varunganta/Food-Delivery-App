package com.example.demo.restaurant;

import com.example.demo.orders.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository){
        this.restaurantRepository = restaurantRepository;
    }

//    public Restaurant createRestaurant(String name){
//        Restaurant restaurant = new Restaurant();
//        restaurant.setRestaurantName("");
//        return restaurantRepository.save(restaurant);
//    }


    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(Long restaurantId) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
        if (restaurantOptional.isPresent()) {
            return restaurantOptional.get();
        } else {
            throw new IllegalArgumentException("Restaurant with ID " + restaurantId + " not found");
        }
    }

    public boolean deleteRestaurantById(Long restaurantId) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
        if (restaurantOptional.isPresent()) {
            ordersService.deleteOrdersByRestaurantId(restaurantId);
            restaurantRepository.deleteById(restaurantId);
            return true;
        } else {
            return false;
        }
    }

    public Restaurant updateRestaurantById(Long restaurantId, RestaurantRequest restaurantRequest) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isEmpty()) {
            return null;
        }

        Restaurant existingRestaurant = optionalRestaurant.get();

        // Update the properties of the existing restaurant based on the request
        //existingRestaurant.setRestaurantName(restaurantRequest.getRestaurantName());
        //existingRestaurant.setRestaurantLocation(restaurantRequest.getRestaurantLocation());
        String updatedName = restaurantRequest.getRestaurantName();
        if (updatedName != null) {
            existingRestaurant.setRestaurantName(updatedName);
        }

        String updatedLocation = restaurantRequest.getRestaurantLocation();
        if (updatedLocation != null) {
            existingRestaurant.setRestaurantLocation(updatedLocation);
        }

        // Save the updated restaurant entity
        return restaurantRepository.save(existingRestaurant);
    }

    public Long getRestaurantIdByAppUserId(Integer appUserId) {
        Restaurant restaurant = restaurantRepository.findRestaurantIdById(appUserId);
        if (restaurant != null) {
            return restaurant.getRestaurantId();
        }
        return null;
    }

//    @Transactional
//    public boolean deleteRestaurantById(Long restaurantId) {
//        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
//        if (restaurantOptional.isPresent()) {
//            restaurantRepository.deleteById(restaurantId);
//            return true;
//        } else {
//            return false;
//        }
//    }
}
