package com.example.demo.restaurant;

import com.example.demo.appuser.AppUser;
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
    //use google map apis for location
    @OneToOne
    @JoinColumn(name = "id")
    private AppUser appUser;

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
