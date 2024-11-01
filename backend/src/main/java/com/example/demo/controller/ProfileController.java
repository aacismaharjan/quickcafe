package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.*;
import com.example.demo.utils.AuthUtil;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import com.example.demo.auth.AuthenticationRequest;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/profile") // Base mapping for the controller
public class ProfileController {
    private final UserService userService;
    private final FavoriteService favoriteService;
    private final OrderService orderService;
    private final AuthUtil authUtil;
    
    @Autowired
    public ProfileController(UserService userService, AuthUtil authUtil, FavoriteService favoriteService, OrderService orderService) {
    	this.userService = userService;
    	this.authUtil = authUtil;
    	this.favoriteService = favoriteService;
        this.orderService = orderService;
    }

    @GetMapping
    public User getCurrentUser(@NonNull HttpServletRequest request) throws Exception {
    	return authUtil.getUserFromRequestToken(request);
    }

    @PutMapping
    public User updateCurrentUser(@NonNull HttpServletRequest request, @RequestBody User user) throws Exception {
        Integer userId = authUtil.getUserIdFromRequestToken(request);
        return userService.updateUser(userId, user);
    }

    @GetMapping("/orders")
    public ResponseEntity<Object> getAllOrderByUserId(@NonNull HttpServletRequest request) {
        try {
            User user = authUtil.getUserFromRequestToken(request);
            return ResponseEntity.ok(orderService.getAllOrderByUserId(user.getId()));
        } catch(Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Object> getOrderById(@NonNull HttpServletRequest request, @PathVariable Long orderId) {
        try {
            User user = authUtil.getUserFromRequestToken(request);
            Optional<Order> order = orderService.getOrderById(orderId);
            if(order.isPresent() && order.get().getUser().equals(user)) {
                return ResponseEntity.ok(order);
            }else {
                throw new Exception("Didn't find your order.");
            }

        } catch(Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@NonNull HttpServletRequest request, @RequestBody Order order) {
        try {
            User user = authUtil.getUserFromRequestToken(request);
            order.setUser(user);
            return ResponseEntity.ok(orderService.createOrder(order));
        }catch(Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<Object> updateOrder(@NonNull HttpServletRequest request, @PathVariable Long orderId, @RequestBody Order order) {
        try {
            User user = authUtil.getUserFromRequestToken(request);
            Optional<Order> fetchedOrder = orderService.getOrderById(orderId);
            if(fetchedOrder.isPresent() && fetchedOrder.get().getUser().equals(user)) {
                orderService.updateOrder(orderId, order);
                return ResponseEntity.ok(order);
            }else {
                throw new Exception("Didn't find your order.");
            }

        } catch(Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<Object> deleteOrder(@NonNull HttpServletRequest request, @PathVariable Long orderId) {
        try {
            int userId = authUtil.getUserIdFromRequestToken(request);
            Optional<Order> fetchedOrder = orderService.getOrderById(orderId);
            if(fetchedOrder.isPresent() && fetchedOrder.get().getUser().getId() == userId) {
                orderService.deleteOrder(orderId);
                return ResponseEntity.noContent().build();
            }else {
                throw new Exception("Didn't find your order.");
            }

        } catch(Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }


    
    @GetMapping(path = "/favorites")
    public List<Favorite> getAllFavorite(@NonNull HttpServletRequest request) throws Exception {
    	Integer userId = authUtil.getUserIdFromRequestToken(request);
        return favoriteService.getAllFavoriteByUserId(userId);
    }

    @PostMapping("/favorites")
    public ResponseEntity<Favorite> addMenuItemToFavorite(@NonNull HttpServletRequest request, @RequestBody Favorite favorite) {
        try {
            User user = authUtil.getUserFromRequestToken(request);
            favorite.setUser(user);
            return ResponseEntity.ok(favoriteService.createFavorite(favorite));
        }catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/favorites/{menuItemId}")
    public ResponseEntity<String> removeMenuItemFromFavorite(@NonNull HttpServletRequest request, @PathVariable Long menuItemId) {
        try {
            Integer userId = authUtil.getUserIdFromRequestToken(request);
            favoriteService.deleteFavoriteByUserIdAndMenuItemId(userId, menuItemId);
            return ResponseEntity.noContent().build();
        }catch(Exception e) {
            return ResponseEntity.badRequest().body("Unable to do UnFavorite: " + e.getMessage());
        }
    }
}
