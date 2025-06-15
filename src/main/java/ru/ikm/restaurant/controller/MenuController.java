package ru.ikm.restaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ikm.restaurant.entity.Menu;
import ru.ikm.restaurant.entity.Restaurant;
import ru.ikm.restaurant.service.MenuService;
import ru.ikm.restaurant.service.RestaurantService;

@Controller
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;
    private final RestaurantService restaurantService;

    public MenuController(MenuService menuService, RestaurantService restaurantService) {
        this.menuService = menuService;
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public String listMenu(Model model) {
        model.addAttribute("menu", menuService.findAll());
        return "list2";
    }

    @GetMapping("/new")
    public String newMenuForm(Model model) {
        model.addAttribute("menu", new Menu());
        model.addAttribute("restaurants", restaurantService.findAll()); // Передаем список ресторанов
        return "new2";
    }

    @PostMapping
    public String createMenu(@ModelAttribute Menu menu, @RequestParam Long restaurantId) {
        Restaurant restaurant = restaurantService.findById(restaurantId);
        if (restaurant == null) {
            throw new IllegalArgumentException("Restaurant not found with ID: " + restaurantId);
        }
        menu.setRestaurant(restaurant);
        menuService.save(menu);
        return "redirect:/menu";
    }

    @PostMapping("/{id}")
    public String updateMenu(@PathVariable Long id, @ModelAttribute Menu menu, @RequestParam Long restaurantId) {
        Restaurant restaurant = restaurantService.findById(restaurantId);
        if (restaurant == null) {
            throw new IllegalArgumentException("Restaurant not found with ID: " + restaurantId);
        }
        menu.setDishId(id);
        menu.setRestaurant(restaurant);
        menuService.save(menu);
        return "redirect:/menu";
    }


    @GetMapping("/{id}/edit")
    public String editMenuForm(@PathVariable Long id, Model model) {
        Menu menu = menuService.findById(id);
        model.addAttribute("menu", menu);
        model.addAttribute("restaurants", restaurantService.findAll()); // Для выбора ресторана
        return "edit2";
    }

    @GetMapping("/{id}/delete")
    public String deleteMenu(@PathVariable Long id) {
        menuService.delete(id);
        return "redirect:/menu";
    }
}
