package com.example.demo.orders;

import com.example.demo.OrderItem.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
//    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.orderId = :orderId")
//    List<OrderItem> findOrderItemsByOrderId(@Param("orderId") Long orderId);
    @Query("SELECT SUM(oi.price) FROM OrderItem oi WHERE oi.orderId = :orderId")
    Long calculateTotalPriceByOrderId(@Param("orderId") Long orderId);

    void deleteByRestaurantId(Long restaurantId);

    @Query("SELECT oi FROM OrderItem oi WHERE oi.orderId = :orderId")
    List<OrderItem> findOrderItemsByOrderId(Long orderId);

    List<Orders> findByRestaurantId(Long restaurantId);

    List<Orders> findByCustomerId(Long customerId);
}
