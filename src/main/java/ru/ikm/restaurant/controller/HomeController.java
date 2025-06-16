package ru.ikm.restaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.ikm.restaurant.entity.Menu;
import ru.ikm.restaurant.entity.Order;
import ru.ikm.restaurant.entity.Restaurant;
import ru.ikm.restaurant.service.MenuService;
import ru.ikm.restaurant.service.OrderService;
import ru.ikm.restaurant.service.RestaurantService;

import java.util.Comparator;
import java.util.List;

@Controller
public class HomeController {

    private final RestaurantService restaurantService;
    private final MenuService menuService;
    private final OrderService orderService;

    public HomeController(RestaurantService restaurantService,
                          MenuService menuService,
                          OrderService orderService) {
        this.restaurantService = restaurantService;
        this.menuService = menuService;
        this.orderService = orderService;
    }

    @GetMapping("/")
    public String index(Model model) {
        // Получаем и сортируем данные по ID в обратном порядке (новые первыми)
        List<Restaurant> restaurants = restaurantService.findAll()
                .stream()
                .sorted(Comparator.comparingLong(Restaurant::getRestaurantId).reversed())
                .limit(5)
                .toList();

        List<Menu> menu = menuService.findAll()
                .stream()
                .sorted(Comparator.comparingLong(Menu::getDishId).reversed())
                .limit(5)
                .toList();

        List<Order> orders = orderService.findAll()
                .stream()
                .sorted(Comparator.comparingLong(Order::getOrderId).reversed())
                .limit(5)
                .toList();

        // Добавляем данные в модель
        model.addAttribute("restaurants", restaurants);
        model.addAttribute("menu", menu);
        model.addAttribute("orders", orders);

        return "index";
    }
}