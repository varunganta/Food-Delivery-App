package com.example.demo.menu;

import com.example.demo.appuser.AppUser;
import com.example.demo.restaurant.Restaurant;
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
public class Menu {
    @Id
    @SequenceGenerator(
            name = "menu_sequence",
            sequenceName = "menu_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "menu_sequence"
    )
    private Long itemId;
    private String itemName;
    private Long itemPrice;

    @Column(name = "restaurant_id")
    private Long restaurantId;
//    @ManyToOne
//    @JoinColumn(name = "restaurant_id")
//    private Restaurant restaurant;
    //

    public Menu(Long itemId,
                      String itemName,
                      Long itemPrice,
                      Long restaurantId) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.restaurantId = restaurantId;
    }

}
