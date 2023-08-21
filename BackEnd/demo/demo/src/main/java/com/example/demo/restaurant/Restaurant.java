package com.example.demo.restaurant;

import com.example.demo.appuser.AppUser;
import com.example.demo.orders.Orders;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Restaurant {
    @Id
    @SequenceGenerator(
            name = "restaurant_sequence",
            sequenceName = "restaurant_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "restaurant_sequence"
    )
    private Long restaurantId;
    private String restaurantName;
    private String restaurantLocation;
    @OneToOne
    @JoinColumn(name = "id")
    private AppUser appUser;

    @OneToMany(mappedBy = "restaurantId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Orders> orders;

    public Restaurant(Long restaurantId,
                      String restaurantName,
                      String restaurantLocation,
                      AppUser appUser) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.restaurantLocation = restaurantLocation;
        this.appUser = appUser;
    }

}
