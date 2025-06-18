package ru.ikm.restaurant.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
        return "menu/list";
    }

    @GetMapping("/new")
    public String newMenuForm(Model model) {
        model.addAttribute("menu", new Menu());
        model.addAttribute("restaurants", restaurantService.findAll()); // Передаем список ресторанов
        return "menu/new";
    }

    @PostMapping
    public String createMenu(@Valid @ModelAttribute("menu") Menu menu,
                             BindingResult result,
                             Model model) {

        if (result.hasErrors()) {
            model.addAttribute("restaurants", restaurantService.findAll());
            return "menu/new";
        }

        // Проверка существования ресторана
        if (!restaurantService.existsById(menu.getRestaurant().getId())) {
            result.rejectValue("restaurant", "error.restaurant", "Ресторан не найден");
            model.addAttribute("restaurants", restaurantService.findAll());
            return "menu/new";
        }

        menuService.save(menu);
        return "redirect:/menu";
    }

    @PostMapping("/edit/{id}")
    public String updateMenu(
            @PathVariable Long id,
            @Valid @ModelAttribute("menu") Menu menu, // Используем @ModelAttribute с именем
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            // Если есть ошибки валидации, возвращаем пользователя на форму.
            // Нужно снова передать список ресторанов для выпадающего списка.
            model.addAttribute("restaurants", restaurantService.findAll());
            // Убедитесь, что ваш шаблон называется form.html
            return "menu/form";
        }

        // Важно! Устанавливаем ID из URL, чтобы быть уверенным,
        // что мы обновляем правильную сущность, а не создаем новую.
        menu.setId(id);

        // Spring уже автоматически привязал ресторан, можно сохранять.
        menuService.save(menu);

        // Исправленный редирект!
        return "redirect:/menu";
    }

    @GetMapping("/edit/{id}")
    public String editMenuForm(@PathVariable Long id, Model model) {
        Menu menu = menuService.findById(id);
        model.addAttribute("menu", menu);
        model.addAttribute("restaurants", restaurantService.findAll()); // Для выбора ресторана
        return "menu/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteMenu(@PathVariable Long id) {
        menuService.delete(id);
        return "redirect:/menu";
    }
}
