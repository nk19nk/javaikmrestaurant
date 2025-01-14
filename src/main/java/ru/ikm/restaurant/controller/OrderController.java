package ru.ikm.restaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ikm.restaurant.entity.Order;
import ru.ikm.restaurant.entity.Restaurant;
import ru.ikm.restaurant.service.OrderService;
import ru.ikm.restaurant.service.RestaurantService;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final RestaurantService restaurantService;

    public OrderController(OrderService orderService, RestaurantService restaurantService) {
        this.orderService = orderService;
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "list3";
    }

    @GetMapping("/new")
    public String newOrderForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("restaurants", restaurantService.findAll());
        return "new3";
    }

    @PostMapping
    public String createOrder(@ModelAttribute Order order, @RequestParam Long restaurantId) {
        Restaurant restaurant = restaurantService.findById(restaurantId);
        if (restaurant != null) {
            order.setRestaurant(restaurant);
            orderService.save(order);
        }
        return "redirect:/orders";
    }

    @GetMapping("/{id}/edit")
    public String editOrderForm(@PathVariable Long id, Model model) {
        Order order = orderService.findById(id);
        if (order == null) {
            throw new IllegalArgumentException("Order not found with ID: " + id);
        }
        model.addAttribute("order", order);
        model.addAttribute("restaurants", restaurantService.findAll());
        return "edit3";
    }

    @PostMapping("/{id}")
    public String updateOrder(@PathVariable Long id, @ModelAttribute Order order, @RequestParam Long restaurantId) {
        order.setOrderId(id);
        Restaurant restaurant = restaurantService.findById(restaurantId);
        if (restaurant != null) {
            order.setRestaurant(restaurant);
            orderService.save(order);
        }
        return "redirect:/orders";
    }

    @GetMapping("/{id}/delete")
    public String deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
        return "redirect:/orders";
    }
}
