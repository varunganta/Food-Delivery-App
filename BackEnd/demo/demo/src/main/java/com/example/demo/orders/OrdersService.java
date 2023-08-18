package com.example.demo.orders;

import com.example.demo.OrderItem.OrderItem;
import com.example.demo.OrderItem.OrderItemRepository;
import com.example.demo.appuser.UserRepository;
import com.example.demo.menu.Menu;
import com.example.demo.menu.MenuRepository;
import com.example.demo.restaurant.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final MenuRepository menuRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public OrdersService(OrdersRepository ordersRepository,
                         MenuRepository menuRepository,
                         OrderItemRepository orderItemRepository,
                         UserRepository userRepository,
                         RestaurantRepository restaurantRepository){
        this.ordersRepository = ordersRepository;
        this.menuRepository = menuRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Orders createOrder(OrdersRequest ordersRequest) {
        Orders newOrder = convertToOrderEntity(ordersRequest);

        if (!isValidOrderItems(newOrder)) {
            throw new IllegalArgumentException("Invalid order items. MenuIds must belong to the same restaurant.");
        }

        Long totalPrice = calculateTotalPrice(newOrder.getOrderId());
        newOrder.setTotalPrice(totalPrice);
        return ordersRepository.save(newOrder);
    }

//    public Orders createOrder(OrdersRequest ordersRequest) {
//        Orders newOrder = convertToOrderEntity(ordersRequest);
//
//        if (!isValidOrderItems(newOrder)) {
//            throw new IllegalArgumentException("Invalid order items. MenuIds must belong to the same restaurant.");
//        }
//
//        List<OrderItem> orderItems = new ArrayList<>();
//
//        for (Long menuId : ordersRequest.getMenuIds()) {
//            Menu menu = menuRepository.findById(menuId)
//                    .orElseThrow(() -> new IllegalArgumentException("Menu with ID " + menuId + " not found"));
//
//            OrderItem orderItem = new OrderItem();
//            orderItem.setMenu(menu);
//            orderItem.setOrder(newOrder);
//            orderItems.add(orderItem);
//        }
//
//        newOrder.setOrderItems(orderItems);
//
//        Long totalPrice = calculateTotalPrice(newOrder.getOrderId());
//        newOrder.setTotalPrice(totalPrice);
//        return ordersRepository.save(newOrder);
//    }

//    @PostMapping("/create")
//    public ResponseEntity<Orders> createOrder(@RequestBody OrdersRequest ordersRequest) {
//        // Get the restaurant associated with the order
//        Restaurant restaurant = restaurantRepository.findById(ordersRequest.getRestaurantId()).orElse(null);
//        if (restaurant == null) {
//            return ResponseEntity.badRequest().body(null); // Invalid restaurant ID
//        }
//
//        // Fetch all menus associated with the restaurant
//        List<Menu> restaurantMenus = menuRepository.findByRestaurantId(ordersRequest.getRestaurantId());
//
//        // Validate orderItems against the restaurant's menus
//        List<OrderItemRequest> orderItems = ordersRequest.getOrderItems();
//        if (orderItems != null) {
//            for (OrderItemRequest orderItemRequest : orderItems) {
//                Long menuId = orderItemRequest.getMenuId();
//                // Check if the menuId exists in the list of restaurantMenus
//                if (restaurantMenus.stream().noneMatch(menu -> menu.getId().equals(menuId))) {
//                    return ResponseEntity.badRequest().body(null); // Invalid menuId for the restaurant
//                }
//            }
//        }
//
//        // If all validation passes, proceed to create the order
//        Orders newOrder = convertToOrderEntity(ordersRequest);
//        Orders createdOrder = ordersService.createOrder(newOrder);
//
//        if (orderItems != null) {
//            for (OrderItemRequest orderItemRequest : orderItems) {
//                OrderItem newOrderItem = new OrderItem();
//                newOrderItem.setOrderId(createdOrder.getOrderId());
//                newOrderItem.setMenuId(orderItemRequest.getMenuId());
//                newOrderItem.setPrice(orderItemRequest.getPrice());
//
//                orderItemService.createOrderItem(newOrderItem);
//            }
//        }
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
//    }

    private boolean isValidOrderItems(Orders orders) {
        Long restaurantId = orders.getRestaurantId();
        List<OrderItem> orderItems = ordersRepository.findOrderItemsByOrderId(orders.getOrderId());
        if (orderItems != null) {
            for (OrderItem orderItem : orderItems) {
                Menu menu = menuRepository.findById(orderItem.getMenuId()).orElse(null);
                if (menu == null || !menu.getRestaurantId().equals(restaurantId)) {
                    return false;
                }
            }
        }
        return true;
    }



    private Orders convertToOrderEntity(OrdersRequest ordersRequest) {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime acceptedAt = createdAt.plusMinutes(1);
        LocalDateTime completedAt = acceptedAt.plusMinutes(30);
        return new Orders(
                null,
                ordersRequest.getCustomerId(),
                ordersRequest.getRestaurantId(),
                ordersRequest.getStatus(),
                createdAt,
                acceptedAt,
                completedAt,
                ordersRequest.getTotalPrice()
        );
    }

    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }

    public Orders getOrderById(Long orderId) {
        return ordersRepository.findById(orderId).orElse(null);
    }

    public Orders updateOrder(Long orderId, Orders updatedOrder) {
        Orders existingOrder = getOrderById(orderId);
        if (existingOrder == null) {
            return null;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String restaurantEmail = authentication.getName();
        int appUserId = userRepository.findByEmail(restaurantEmail).get().getId();
        Long restaurantId = restaurantRepository.findByAppUserId(appUserId);
        boolean isAuthorized = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("RESTAURANT")
                        && existingOrder.getRestaurantId().equals(restaurantId));

        if (!isAuthorized) {
            return null;
        }

        if (updatedOrder.getStatus() != null) {
            existingOrder.setStatus(updatedOrder.getStatus());
        }

        if (updatedOrder.getAcceptedAt() != null) {
            existingOrder.setAcceptedAt(updatedOrder.getAcceptedAt());
        }

        if (updatedOrder.getCompletedAt() != null) {
            existingOrder.setCompletedAt(updatedOrder.getCompletedAt());
        }

        Long updatedCustomer = updatedOrder.getCustomerId();
        if (updatedCustomer != null) {
            existingOrder.setCustomerId(updatedCustomer);
        }

        Long updatedRestaurant = updatedOrder.getRestaurantId();
        if (updatedRestaurant != null) {
            existingOrder.setRestaurantId(updatedRestaurant);
        }

        return ordersRepository.save(existingOrder);
    }

    @Transactional
    public boolean deleteOrder(Long orderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String restaurantEmail = authentication.getName();
        int appUserId = userRepository.findByEmail(restaurantEmail).get().getId();
        Long restaurantId = restaurantRepository.findByAppUserId(appUserId);

        Orders order = ordersRepository.findById(orderId).orElse(null);
        if (order == null) {
            return false;
        }

        boolean isAuthorized = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("RESTAURANT")
                        && order.getRestaurantId().equals(restaurantId));

        if (!isAuthorized) {
            return false;
        }

        orderItemRepository.deleteByOrderId(orderId);
        ordersRepository.delete(order);

        return true;
    }

//    public Long calculateTotalPrice(Orders order) {
//        Long totalPrice = ordersRepository.calculateTotalPriceByOrderId(order.getOrderId());
//        return totalPrice != null ? totalPrice : 0L;
//    }
    public Long calculateTotalPrice(Long orderId) {
        List<OrderItem> orderItems = orderItemRepository.findOrderItemsByOrderId(orderId);
        if (orderItems != null) {
            return (long) orderItems.stream().mapToDouble(OrderItem::getPrice).sum();
        } else {
            return 0L;
        }
    }

    @Transactional
    public void deleteOrdersByRestaurantId(Long restaurantId){
        ordersRepository.deleteByRestaurantId(restaurantId);
    }

    public List<Orders> getOrdersByRestaurantId(Long restaurantId) {
        return ordersRepository.findByRestaurantId(restaurantId);
    }

    public List<Orders> getOrdersByCustomerId(Long customerId) {
        return ordersRepository.findByCustomerId(customerId);
    }
}
