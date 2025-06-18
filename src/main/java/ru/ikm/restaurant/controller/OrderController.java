// TODO: Написать контроллер для работы с сущностью Order
package ru.ikm.restaurant.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ikm.restaurant.entity.Order;
import ru.ikm.restaurant.service.MenuService;
import ru.ikm.restaurant.service.OrderService;

/**
 * Контроллер для работы с сущностью "Заказ".
 * Обрабатывает HTTP-запросы, связанные с отображением, созданием, редактированием и удалением заказов.
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final MenuService menuService;

    /**
     * Конструктор с внедрением зависимостей сервисов.
     *
     * @param orderService Сервис для работы с заказами
     * @param menuService  Сервис для работы с блюдами (меню)
     */
    public OrderController(OrderService orderService,
                           MenuService menuService) {
        this.orderService = orderService;
        this.menuService = menuService;
    }

    /**
     * Отображает список всех заказов.
     *
     * @param model Объект модели для передачи данных во view
     * @return Имя шаблона "order/list"
     */
    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute(
                "orders", orderService.findAll()
        );
        return "order/list";
    }

    /**
     * Отображает форму создания нового заказа.
     *
     * @param model Объект модели для передачи данных во view
     * @return Имя шаблона "order/new"
     */
    @GetMapping("/new")
    public String newOrderForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("dishes", menuService.findAll());
        return "order/new";
    }

    /**
     * Обрабатывает запрос на создание нового заказа.
     * Проверяет валидность данных перед сохранением.
     *
     * @param order   Объект заказа, полученный из формы
     * @param result  Результат валидации
     * @param model   Объект модели для передачи данных во view
     * @return Имя шаблона "order/new" при ошибках или перенаправление на список заказов
     */
    @PostMapping
    public String createOrder(
            @Valid @ModelAttribute("order") Order order,
            BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("dishes", menuService.findAll());
            return "order/new";
        }
        orderService.save(order);
        return "redirect:/order";
    }

    /**
     * Отображает форму редактирования заказа по его ID.
     *
     * @param id    Идентификатор заказа
     * @param model Объект модели для передачи данных во view
     * @return Имя шаблона "order/form"
     * @throws IllegalArgumentException если заказ не найден
     */
    @GetMapping("/edit/{id}")
    public String editOrderForm(@PathVariable Long id, Model model) {
        Order order = orderService.findById(id);
        if (order == null) {
            throw new IllegalArgumentException("Order not found with ID: " + id);
        }
        model.addAttribute("order", order);
        model.addAttribute("dishes", menuService.findAll());
        return "order/form";
    }

    /**
     * Обрабатывает запрос на обновление существующего заказа.
     *
     * @param id      Идентификатор заказа
     * @param order   Объект заказа, полученный из формы
     * @param result  Результат валидации
     * @param model   Объект модели для передачи данных во view
     * @return Имя шаблона "order/form" при ошибках или перенаправление на список заказов
     */
    @PostMapping("/{id}")
    public String updateOrder(
            @PathVariable Long id,
            @Valid @ModelAttribute("order") Order order,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("dishes", menuService.findAll());
            return "order/form";
        }

        order.setId(id);
        orderService.save(order);
        return "redirect:/order";
    }

    /**
     * Удаляет заказ по его ID.
     *
     * @param id Идентификатор заказа
     * @return Перенаправление на список заказов
     */
    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
        return "redirect:/order";
    }
}