package com.example.demo.Cart;

import com.example.demo.appuser.AppUser;
import com.example.demo.menu.Menu;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @Column(name = "user_id")
    private Long appUserId;

    @Column(name = "item_id")
    private Long itemId;

    private int quantity;

    public Cart(Long appUserId, Long itemId, int quantity) {
        this.appUserId = appUserId;
        this.itemId = itemId;
        this.quantity = quantity;
    }
}

