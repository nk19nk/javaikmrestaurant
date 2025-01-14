package ru.ikm.restaurant.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ikm.restaurant.entity.Restaurant;
import ru.ikm.restaurant.service.RestaurantService;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/list")
    public String listRestaurants(Model model) {
        model.addAttribute("restaurants", restaurantService.findAll());
        return "list";
    }

    @GetMapping("/new")
    public String newRestaurantForm(Model model) {
        model.addAttribute("restaurant", new Restaurant());
        return "new";
    }

    @PostMapping
    public String createRestaurant(@ModelAttribute Restaurant restaurant) {
        restaurantService.save(restaurant);
        return "redirect:/restaurants/list";
    }

    @GetMapping("/edit/{id}")
    public String editRestaurantForm(@PathVariable Long id, Model model) {
        Restaurant restaurant = restaurantService.findById(id);
        model.addAttribute("restaurant", restaurant);
        return "edit";  // Шаблон для редактирования
    }

    @PostMapping("/update/{id}")
    public String updateRestaurant(@PathVariable Long id, @ModelAttribute Restaurant restaurant) {
        restaurant.setRestaurantId(id);
        restaurantService.save(restaurant);
        return "redirect:/restaurants/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteRestaurant(@PathVariable Long id) {
        restaurantService.delete(id);
        return "redirect:/restaurants/list";
    }
}

