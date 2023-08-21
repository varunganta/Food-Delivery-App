package com.example.demo.OrderItem;

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
public class OrderItem {
    @Id
    @SequenceGenerator(
            name = "order_item_sequence",
            sequenceName = "order_item_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_item_sequence"
    )
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "order_id")
    @Column(name = "order_id")
    private Long orderId;

//    @ManyToOne
//    @JoinColumn(name = "menu_id")
    @Column(name = "menu_id")
    private Long menuId;

    private double price;

    private Long quantity;

//    @OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL)
//    private List<Orders> orders;

    public OrderItem(Long orderId, Long menuId, double price, Long quantity) {
        this.orderId = orderId;
        this.menuId = menuId;
        this.price = price;
        this.quantity = quantity;
    }
}
