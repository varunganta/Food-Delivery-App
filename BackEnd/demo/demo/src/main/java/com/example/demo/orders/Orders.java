package com.example.demo.orders;

//import com.example.demo.OrderItem.OrderItem;
import com.example.demo.OrderItem.OrderItem;
import com.example.demo.appuser.AppUser;
import com.example.demo.restaurant.Restaurant;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.utility.nullability.MaybeNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Orders {
    @Id
    @SequenceGenerator(
            name = "orders_sequence",
            sequenceName = "orders_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "orders_sequence"
    )
    private Long orderId;

//    @ManyToOne
//    @JoinColumn(name = "customer_id")
    @Column(name = "customer_id")
    private Long customerId;

//    @ManyToOne
//    @JoinColumn(name = "restaurant_id")
    @Column(name = "restaurant_id")
    private Long restaurantId;
    private String status;
    //rider details
    private LocalDateTime createdAt;
    private LocalDateTime acceptedAt;
    private LocalDateTime completedAt;
    private Long totalPrice;

//    @Column(name = "item_id")
//    private Long itemId;

//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
//    private List<OrderItem> orderItems;

//    @OneToMany
//    @JoinColumn(name = "order_id")
//    private List<OrderItem> orderItem;

    public Orders(Long orderId,
                      Long customerId,
                      Long restaurantId,
                      String status,
                  LocalDateTime createdAt,
                  LocalDateTime acceptedAt,
                  LocalDateTime completedAt,
                  Long totalPrice
                  ) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.status = status;
        this.createdAt = createdAt;
        this.acceptedAt = acceptedAt;
        this.completedAt = completedAt;
        this.totalPrice = totalPrice;
    }

    public Orders() {

    }

}
