// TODO: Написать контроллер для работы с сущностью Restaurant
package ru.ikm.restaurant.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ikm.restaurant.entity.Restaurant;
import ru.ikm.restaurant.service.RestaurantService;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public String listRestaurants(Model model) {
        model.addAttribute(
                "restaurants", restaurantService.findAll()
        );
        return "restaurant/list";
    }

    @GetMapping("/new")
    public String newRestaurantForm(Model model) {
        model.addAttribute("restaurant", new Restaurant());
        return "restaurant/new";
    }

    @PostMapping
    public String createRestaurant(
            @Valid @ModelAttribute Restaurant restaurant,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "restaurant/new";
        }
        restaurantService.save(restaurant);
        return "redirect:/restaurant";
    }

    @GetMapping("/edit/{id}")
    public String editRestaurantForm(@PathVariable Long id, Model model) {
        Restaurant restaurant = restaurantService.findById(id);
        if (restaurant == null) {
            return "redirect:/restaurant";
        }
        model.addAttribute("restaurant", restaurant);
        return "restaurant/form";
    }

    @PostMapping("/{id}")
    public String updateRestaurant(
            @PathVariable Long id,
            @Valid @ModelAttribute Restaurant restaurant,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "restaurant/form";
        }
        restaurant.setId(id);
        restaurantService.save(restaurant);
        return "redirect:/restaurant";
    }

    @PostMapping("/delete/{id}")
    public String deleteRestaurant(@PathVariable Long id) {
        restaurantService.delete(id);
        return "redirect:/restaurant";
    }
}