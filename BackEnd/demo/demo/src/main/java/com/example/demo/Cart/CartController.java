package com.example.demo.Cart;

import com.example.demo.appuser.AppUser;
import com.example.demo.menu.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin("http://localhost:3000")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addItemToCart(@RequestBody CartRequest cartRequest) {
        Long itemId = cartRequest.getItemId();
        Long appUserId = cartRequest.getAppUserId();
        int quantity = cartRequest.getQuantity();

        Cart cartItem = cartService.addItemToCart(itemId, appUserId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItem);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Cart>> getCartItemsByUser(@PathVariable Long userId) {
        List<Cart> cartItems = cartService.getCartItemsByUser(userId);
        return ResponseEntity.ok(cartItems);
    }

    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<Cart> updateCartItemQuantity(
            @PathVariable Long cartItemId,
            @RequestBody CartRequest cartRequest
    ) {
        int newQuantity = cartRequest.getQuantity();

        Cart updatedCartItem = cartService.updateCartItemQuantity(cartItemId, newQuantity);
        return ResponseEntity.ok(updatedCartItem);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long cartItemId) {
        cartService.removeCartItem(cartItemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all/{userId}")
    public ResponseEntity<Void> emptyCartByUser(@PathVariable Long userId) {
        cartService.emptyCartByUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/totalPrice/{userId}")
    public ResponseEntity<Double> getTotalPriceByUser(@PathVariable Long userId) {
        Double totalPrice = cartService.getTotalPriceByUser(userId);
        return ResponseEntity.ok(totalPrice);
    }
}
