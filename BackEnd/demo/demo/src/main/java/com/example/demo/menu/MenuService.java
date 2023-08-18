package com.example.demo.menu;

import com.example.demo.appuser.UserRepository;
import com.example.demo.restaurant.Restaurant;
import com.example.demo.restaurant.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository, RestaurantRepository restaurantRepository, UserRepository userRepository){
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }


    public Menu getMenuById(Long itemId) {
        Optional<Menu> menuOptional = menuRepository.findById(itemId);
        return menuOptional.orElse(null);
    }

    public Menu createMenu(MenuRequest menuRequest) {
        Long restaurantId = menuRequest.getRestaurantId();
        String itemName = menuRequest.getItemName();
        Long itemPrice = menuRequest.getItemPrice();

        if(restaurantId == null || itemName == null || itemName.trim().isEmpty() || !itemName.matches("^[a-zA-Z\\s]*$") || itemPrice <= 0){
            throw new IllegalArgumentException("Invalid input data. Restaurant ID, item name, and item price are required.");
        }

        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);

        if (restaurantOptional.isPresent()) {
            Restaurant restaurant = restaurantOptional.get();

            Menu newMenu = new Menu();
            newMenu.setItemName(itemName);
            newMenu.setItemPrice(itemPrice);
            newMenu.setRestaurantId(restaurantId);

            return menuRepository.save(newMenu);
        } else {
            throw new IllegalArgumentException("Restaurant with ID " + restaurantId + " not found");
        }
    }

    public Menu updateMenuById(Long itemId, MenuRequest menuRequest) {
        Menu existingMenu = menuRepository.findById(itemId).orElse(null);
        if (existingMenu == null) {
            return null;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String restaurantEmail = authentication.getName();
        int appUserId = userRepository.findByEmail(restaurantEmail).get().getId();
        Long restaurantId = restaurantRepository.findByAppUserId(appUserId);
        boolean isAuthorized = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("RESTAURANT")
                        && existingMenu.getRestaurantId().equals(restaurantId));

        if (!isAuthorized) {
            return null;
        }

        String itemName = menuRequest.getItemName();
        if (itemName != null && !itemName.isEmpty()) {
            existingMenu.setItemName(itemName);
        }

        Long itemPrice = menuRequest.getItemPrice();
        if (itemPrice != null) {
            existingMenu.setItemPrice(itemPrice);
        }

        return menuRepository.save(existingMenu);
    }

//    public boolean deleteMenuById(Long itemId) {
//        if(menuRepository.existsById(itemId)){
//            menuRepository.deleteById(itemId);
//            return true;
//        }else {
//            return false;
//        }
//    }
    public boolean deleteMenuById(Long itemId) {
        Menu existingMenu = menuRepository.findById(itemId).orElse(null);
        if (existingMenu == null) {
            return false;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String restaurantEmail = authentication.getName();
        int appUserId = userRepository.findByEmail(restaurantEmail).get().getId();
        Long restaurantId = restaurantRepository.findByAppUserId(appUserId);

        boolean isAuthorized = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("RESTAURANT")
                        && existingMenu.getRestaurantId().equals(restaurantId));

        if (!isAuthorized) {
            return false;
        }

        menuRepository.deleteById(itemId);
        return true;
    }
    public boolean canAccessMenu(Long menuId, Long restaurantId){
        Menu menu = getMenuById(menuId);
        return menu != null && menu.getRestaurantId().equals(restaurantId);
    }

    public List<Menu> getMenuByRestaurantId(Long restaurantId) {
        return menuRepository.findByRestaurantId(restaurantId);
    }
}
