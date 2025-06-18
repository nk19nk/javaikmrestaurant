// TODO: Написать контроллер для отображения главной страницы
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

// Добавлено каскадное удаление
// Добавлена валидация входных данных
// Убраны лишние комментарии, в том числе закомментированные методы
// Добавлена главная страница и навигационная панель для удобства пользователя
// В order добавлено поле dish для обеспечения корректной бизнес-логики
// Устранены несоответствия в маршрутах между контроллерами и шаблонами, приведя их к единому стилю
// Добавлена логическая группировка шаблонов
// Добавлено TODO
// Убраны строки длиной более 100 символов
// Приведено оформление фигурных скобок к единому стилю (открывающая скобка на новой строке после условия)

/**
 * Контроллер для отображения главной страницы приложения.
 * Обрабатывает GET-запрос по корневому пути "/" и предоставляет данные для отображения
 * на главной странице index.html.
 */
@Controller
public class HomeController {

    private final RestaurantService restaurantService;
    private final MenuService menuService;
    private final OrderService orderService;

    /**
     * Конструктор с внедрением зависимостей сервисов.
     *
     * @param restaurantService Сервис для работы с ресторанами
     * @param menuService       Сервис для работы с меню
     * @param orderService      Сервис для работы с заказами
     */
    public HomeController(RestaurantService restaurantService,
                          MenuService menuService,
                          OrderService orderService) {
        this.restaurantService = restaurantService;
        this.menuService = menuService;
        this.orderService = orderService;
    }

    /**
     * Обрабатывает GET-запрос по адресу "/".
     * Получает последние 5 записей из таблиц Restaurant, Menu и Order,
     * сортирует их по убыванию ID и передаёт во view для отображения.
     *
     * @param model Объект модели для передачи данных во view
     * @return Имя шаблона "index"
     */
    @GetMapping("/")
    public String index(Model model) {
        // Получаем и сортируем данные по ID в обратном порядке (новые первыми)
        List<Restaurant> restaurants = restaurantService.findAll()
                .stream()
                .sorted(Comparator.comparingLong(Restaurant::getId).reversed())
                .limit(5)
                .toList();

        List<Menu> menu = menuService.findAll()
                .stream()
                .sorted(Comparator.comparingLong(Menu::getId).reversed())
                .limit(5)
                .toList();

        List<Order> orders = orderService.findAll()
                .stream()
                .sorted(Comparator.comparingLong(Order::getId).reversed())
                .limit(5)
                .toList();

        // Добавляем данные в модель
        model.addAttribute("restaurants", restaurants);
        model.addAttribute("menu", menu);
        model.addAttribute("orders", orders);

        return "index";
    }
}