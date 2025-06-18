// TODO: Написать контроллер для работы с сущностью Menu
package ru.ikm.restaurant.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ikm.restaurant.entity.Menu;
import ru.ikm.restaurant.service.MenuService;
import ru.ikm.restaurant.service.RestaurantService;

@Controller
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;
    private final RestaurantService restaurantService;

    public MenuController(MenuService menuService,
                          RestaurantService restaurantService) {
        this.menuService = menuService;
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public String listMenu(Model model) {
        model.addAttribute(
                "menu", menuService.findAll()
        );
        return "menu/list";
    }

    @GetMapping("/new")
    public String newMenuForm(Model model) {
        model.addAttribute("menu", new Menu());
        model.addAttribute(
                "restaurants", restaurantService.findAll()
        );
        return "menu/new";
    }

    @PostMapping
    public String createMenu(@Valid @ModelAttribute("menu") Menu menu,
                             BindingResult result,
                             Model model) {

        if (result.hasErrors()) {
            model.addAttribute(
                    "restaurants", restaurantService.findAll()
            );
            return "menu/new";
        }

        if (!restaurantService.existsById(menu.getRestaurant().getId())) {
            result.rejectValue("restaurant", "error.restaurant",
                    "Ресторан не найден");
            model.addAttribute(
                    "restaurants", restaurantService.findAll()
            );
            return "menu/new";
        }

        menuService.save(menu);
        return "redirect:/menu";
    }

    @PostMapping("/edit/{id}")
    public String updateMenu(
            @PathVariable Long id,
            @Valid @ModelAttribute("menu") Menu menu,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute(
                    "restaurants", restaurantService.findAll()
            );
            return "menu/form";
        }

        menu.setId(id);
        menuService.save(menu);
        return "redirect:/menu";
    }

    @GetMapping("/edit/{id}")
    public String editMenuForm(@PathVariable Long id, Model model) {
        Menu menu = menuService.findById(id);
        model.addAttribute("menu", menu);
        model.addAttribute(
                "restaurants", restaurantService.findAll()
        );
        return "menu/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteMenu(@PathVariable Long id) {
        menuService.delete(id);
        return "redirect:/menu";
    }
}
