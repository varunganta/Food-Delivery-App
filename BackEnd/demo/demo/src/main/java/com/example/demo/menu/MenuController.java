package com.example.demo.menu;

import com.example.demo.restaurant.Restaurant;
import com.example.demo.restaurant.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menus")
@CrossOrigin("http://localhost:3000")
public class MenuController {
    private final MenuService menuService;
    private final RestaurantService restaurantService;

    @Autowired
    public MenuController(MenuService menuService, RestaurantService restaurantService){
        this.menuService = menuService;
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<List<Menu>> getAllMenus() {
        List<Menu> menus = menuService.getAllMenus();
        return ResponseEntity.ok(menus);
    }

    @GetMapping("menu/{itemId}")
    public ResponseEntity<Menu> getMenuById(@PathVariable Long itemId) {
        Menu menu = menuService.getMenuById(itemId);
        if (menu != null) {
            return ResponseEntity.ok(menu);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Menu> createMenu(@RequestBody MenuRequest menuRequest) {
        Restaurant restaurant = restaurantService.getRestaurantById(menuRequest.getRestaurantId());
        if(restaurant == null){
            return ResponseEntity.notFound().build();
        }

        Menu newMenu = menuService.createMenu(menuRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(newMenu);
    }

    @PutMapping("menu/{itemId}")
    public ResponseEntity<Menu> updateMenuById(@PathVariable Long itemId, @RequestBody MenuRequest menuRequest) {
        Menu updatedMenu = menuService.updateMenuById(itemId, menuRequest);
        if (updatedMenu != null) {
            return ResponseEntity.ok(updatedMenu);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("menu/{itemId}")
    public ResponseEntity<Void> deleteMenuById(@PathVariable Long itemId) {
        boolean isDeleted = menuService.deleteMenuById(itemId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
