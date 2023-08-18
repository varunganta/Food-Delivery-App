package com.example.demo.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    @Query("SELECT m FROM Menu m WHERE m.restaurantId = ?1 AND m.itemName = ?2")
    Menu findMenuByCriteria(Long restaurantId, String itemName);

    List<Menu> findByRestaurantId(Long restaurantId);
}
