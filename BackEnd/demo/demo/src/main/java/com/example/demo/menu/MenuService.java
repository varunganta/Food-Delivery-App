package com.example.demo.menu;

import com.example.demo.appuser.AppUser;
import com.example.demo.restaurant.Restaurant;
import com.example.demo.restaurant.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository, RestaurantRepository restaurantRepository){
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }


    public Menu getMenuById(Long itemId) {
        Optional<Menu> menuOptional = menuRepository.findById(itemId);
        return menuOptional.orElse(null);
    }

    public Menu createMenu(MenuRequest menuRequest) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(menuRequest.getRestaurantId());
//        if (!canAccessMenu(itemId, restaurantId)) {
//            throw new AccessDeniedException("Unauthorized to update the menu for Menu ID " + itemId);
//        }
        if(restaurantOptional.isPresent()){
            Restaurant restaurant = restaurantOptional.get();

            Menu newMenu = new Menu();
            newMenu.setItemName(menuRequest.getItemName());
            newMenu.setItemPrice(menuRequest.getItemPrice());
            newMenu.setRestaurantId(menuRequest.getRestaurantId());
            return menuRepository.save(newMenu);
        } else{
            throw new IllegalArgumentException("Restaurant with ID " + menuRequest.getRestaurantId() + " not found");
        }
    }
//    public Menu createMenu(MenuRequest menuRequest) {
//        // Get the authenticated user's details from the SecurityContextHolder
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        // Assuming your UserDetails implementation has a method to retrieve the restaurantId of the user
//        Long authenticatedRestaurantId = AppUser.getRestaurantId();
//
//        // Check if the authenticated user's restaurantId matches the restaurantId in the MenuRequest
//        if (authenticatedRestaurantId.equals(menuRequest.getRestaurantId())) {
//            // Use a method in your MenuRepository to find the menu entry based on the provided criteria
//            // For example, if you have a findMenuByCriteria method in your MenuRepository:
//            Menu existingMenu = menuRepository.findMenuByCriteria(menuRequest.getRestaurantId(), menuRequest.getItemName());
//
//            if (existingMenu != null) {
//                throw new IllegalArgumentException("Menu entry with the same name already exists for this restaurant.");
//            } else {
//                Menu newMenu = new Menu();
//                newMenu.setItemName(menuRequest.getItemName());
//                newMenu.setItemPrice(menuRequest.getItemPrice());
//                newMenu.setRestaurantId(menuRequest.getRestaurantId());
//
//                return menuRepository.save(newMenu);
//            }
//        } else {
//            throw new IllegalArgumentException("You are not authorized to create a menu for this restaurant.");
//        }
//    }

    public Menu updateMenuById(Long itemId, MenuRequest menuRequest) {
        Optional<Menu> menuOptional = menuRepository.findById(itemId);
        if(menuOptional.isPresent()){
            Menu existingMenu = menuOptional.get();
            existingMenu.setItemName(menuRequest.getItemName());
            existingMenu.setItemPrice(menuRequest.getItemPrice());
            return menuRepository.save(existingMenu);
        } else {
            return null;
        }
    }

    public boolean deleteMenuById(Long itemId) {
        if(menuRepository.existsById(itemId)){
            menuRepository.deleteById(itemId);
            return true;
        }else {
            return false;
        }
    }

    public boolean canAccessMenu(Long menuId, Long restaurantId){
        Menu menu = getMenuById(menuId);
        return menu != null && menu.getRestaurantId().equals(restaurantId);
    }

}
