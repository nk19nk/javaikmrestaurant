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

/**
 * Контроллер для работы с сущностью "Ресторан".
 * Обрабатывает HTTP-запросы, связанные с отображением, созданием, редактированием и удалением ресторанов.
 */
@Controller
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    /**
     * Конструктор с внедрением зависимости сервиса для работы с ресторанами.
     *
     * @param restaurantService Сервис для работы с сущностью Restaurant
     */
    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    /**
     * Отображает список всех ресторанов.
     *
     * @param model Объект модели для передачи данных во view
     * @return Имя шаблона "restaurant/list"
     */
    @GetMapping
    public String listRestaurants(Model model) {
        model.addAttribute(
                "restaurants", restaurantService.findAll()
        );
        return "restaurant/list";
    }

    /**
     * Отображает форму создания нового ресторана.
     *
     * @param model Объект модели для передачи данных во view
     * @return Имя шаблона "restaurant/new"
     */
    @GetMapping("/new")
    public String newRestaurantForm(Model model) {
        model.addAttribute("restaurant", new Restaurant());
        return "restaurant/new";
    }

    /**
     * Обрабатывает запрос на создание нового ресторана.
     * Проверяет валидность введённых данных.
     *
     * @param restaurant Объект ресторана, полученный из формы
     * @param result     Результат валидации
     * @return Имя шаблона "restaurant/new" при ошибках или перенаправление на список ресторанов
     */
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

    /**
     * Отображает форму редактирования ресторана по его ID.
     *
     * @param id    Идентификатор ресторана
     * @param model Объект модели для передачи данных во view
     * @return Имя шаблона "restaurant/form"
     */
    @GetMapping("/edit/{id}")
    public String editRestaurantForm(@PathVariable Long id, Model model) {
        Restaurant restaurant = restaurantService.findById(id);
        if (restaurant == null) {
            return "redirect:/restaurant";
        }
        model.addAttribute("restaurant", restaurant);
        return "restaurant/form";
    }

    /**
     * Обрабатывает запрос на обновление существующего ресторана.
     *
     * @param id         Идентификатор ресторана
     * @param restaurant Объект ресторана, полученный из формы
     * @param result     Результат валидации
     * @return Имя шаблона "restaurant/form" при ошибках или перенаправление на список ресторанов
     */
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

    /**
     * Удаляет ресторан по его ID.
     *
     * @param id Идентификатор ресторана
     * @return Перенаправление на список ресторанов
     */
    @PostMapping("/delete/{id}")
    public String deleteRestaurant(@PathVariable Long id) {
        restaurantService.delete(id);
        return "redirect:/restaurant";
    }
}