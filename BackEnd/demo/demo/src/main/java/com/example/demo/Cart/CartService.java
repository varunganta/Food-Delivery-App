package com.example.demo.Cart;

import com.example.demo.appuser.AppUser;
import com.example.demo.menu.Menu;
import com.example.demo.menu.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.awt.*;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final MenuRepository menuRepository;

    @Autowired
    public CartService(CartRepository cartRepository, MenuRepository menuRepository) {
        this.cartRepository = cartRepository;
        this.menuRepository = menuRepository;
    }

    public Cart addItemToCart(Long itemId, Long appUserId, int quantity) {
        Cart existingCartItem = cartRepository.findByAppUserIdAndItemId(appUserId, itemId);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            return cartRepository.save(existingCartItem);
        }

        Cart newCartItem = new Cart();
        newCartItem.setAppUserId(appUserId);
        newCartItem.setItemId(itemId);
        newCartItem.setQuantity(quantity);
        return cartRepository.save(newCartItem);
    }

//    public Cart addItemToCart(Long itemId, Long appUserId, int quantity, Long restaurantId) {
//        // Fetch the menu item to validate the restaurantId
//        Menu menuItem = menuRepository.findById(itemId)
//                .orElseThrow(() -> new RuntimeException("Menu item not found"));
//
//        if (!menuItem.getRestaurantId().equals(restaurantId)) {
//            throw new IllegalArgumentException("Menu item does not belong to the selected restaurant");
//        }
//
//        Cart existingCartItem = cartRepository.findByAppUserIdAndItemId(appUserId, itemId);
//
//        if (existingCartItem != null) {
//            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
//            return cartRepository.save(existingCartItem);
//        }
//
//        Cart newCartItem = new Cart();
//        newCartItem.setAppUserId(appUserId);
//        newCartItem.setItemId(itemId);
//        newCartItem.setQuantity(quantity);
//        return cartRepository.save(newCartItem);
//    }

    public List<Cart> getCartItemsByUser(Long appUserId) {
        return cartRepository.findByAppUserId(appUserId);
    }

    public void removeCartItem(Long cartItemId) {
        cartRepository.deleteById(cartItemId);
    }

    public Double getTotalPriceByUser(Long userId) {
        List<Cart> cartItems = cartRepository.findByAppUserId(userId);
        Double totalPrice = 0.0;

        for (Cart cartItem : cartItems) {
            Menu menuItem = menuRepository.findById(cartItem.getItemId())
                    .orElseThrow(() -> new EntityNotFoundException("Menu item not found"));

            totalPrice += menuItem.getItemPrice() * cartItem.getQuantity();
        }

        return totalPrice;
    }

    public Cart updateCartItemQuantity(Long cartItemId, int newQuantity) {
        Cart cartItem = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItem.setQuantity(newQuantity);
        return cartRepository.save(cartItem);
    }

    @Transactional
    public void emptyCartByUser(Long userId) {
        cartRepository.deleteAllByAppUserId(userId);
    }
}
