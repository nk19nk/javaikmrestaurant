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

/**
 * Контроллер для работы с сущностью "Меню".
 * Обрабатывает HTTP-запросы, связанные с отображением, созданием, редактированием и удалением меню.
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;
    private final RestaurantService restaurantService;

    /**
     * Конструктор с внедрением зависимостей сервисов.
     *
     * @param menuService         Сервис для работы с меню
     * @param restaurantService   Сервис для работы с ресторанами
     */
    public MenuController(MenuService menuService,
                          RestaurantService restaurantService) {
        this.menuService = menuService;
        this.restaurantService = restaurantService;
    }

    /**
     * Отображает список всех меню.
     *
     * @param model Объект модели для передачи данных во view
     * @return Имя шаблона "menu/list"
     */
    @GetMapping
    public String listMenu(Model model) {
        model.addAttribute(
                "menu", menuService.findAll()
        );
        return "menu/list";
    }

    /**
     * Отображает форму создания нового меню.
     *
     * @param model Объект модели для передачи данных во view
     * @return Имя шаблона "menu/new"
     */
    @GetMapping("/new")
    public String newMenuForm(Model model) {
        model.addAttribute("menu", new Menu());
        model.addAttribute(
                "restaurants", restaurantService.findAll()
        );
        return "menu/new";
    }

    /**
     * Обрабатывает запрос на создание нового меню.
     * Проверяет валидность данных и наличие указанного ресторана.
     *
     * @param menu    Объект меню, полученный из формы
     * @param result  Результат валидации
     * @param model   Объект модели для передачи данных во view
     * @return Имя шаблона "menu/new" при ошибках или перенаправление на список меню
     */
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

    /**
     * Обрабатывает запрос на обновление существующего меню.
     *
     * @param id      Идентификатор меню
     * @param menu    Объект меню, полученный из формы
     * @param result  Результат валидации
     * @param model   Объект модели для передачи данных во view
     * @return Имя шаблона "menu/form" при ошибках или перенаправление на список меню
     */
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

    /**
     * Отображает форму редактирования меню по его ID.
     *
     * @param id    Идентификатор меню
     * @param model Объект модели для передачи данных во view
     * @return Имя шаблона "menu/form"
     */
    @GetMapping("/edit/{id}")
    public String editMenuForm(@PathVariable Long id, Model model) {
        Menu menu = menuService.findById(id);
        model.addAttribute("menu", menu);
        model.addAttribute(
                "restaurants", restaurantService.findAll()
        );
        return "menu/form";
    }

    /**
     * Удаляет меню по его ID.
     *
     * @param id Идентификатор меню
     * @return Перенаправление на список меню
     */
    @GetMapping("/delete/{id}")
    public String deleteMenu(@PathVariable Long id) {
        menuService.delete(id);
        return "redirect:/menu";
    }
}
