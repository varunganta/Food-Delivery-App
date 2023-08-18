package com.example.demo.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("SELECT r.restaurantId FROM Restaurant r WHERE r.appUser.id = :appUserId")
    Long findByAppUserId(int appUserId);

    @Query("SELECT r FROM Restaurant r WHERE r.appUser.id = :appUserId")
    Restaurant findRestaurantIdById(@Param("appUserId") Integer appUserId);
}
