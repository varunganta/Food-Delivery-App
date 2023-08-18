package com.example.demo.Cart;

import com.example.demo.appuser.AppUser;
import com.example.demo.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByAppUserId(Long appUserId);

    Cart findByAppUserIdAndItemId(Long appUserId, Long itemId);

    void deleteAllByAppUserId(Long appUserId);
}
