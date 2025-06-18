package ru.ikm.restaurant.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ikm.restaurant.entity.Order;
import ru.ikm.restaurant.service.MenuService;
import ru.ikm.restaurant.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final MenuService menuService;

    public OrderController(OrderService orderService, MenuService menuService) {
        this.orderService = orderService;
        this.menuService = menuService;
    }

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "order/list";
    }

    @GetMapping("/new")
    public String newOrderForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("dishes", menuService.findAll());
        return "order/new";
    }

    // --- ИСПРАВЛЕННЫЙ МЕТОД CREATE ---
    @PostMapping
    public String createOrder(@Valid @ModelAttribute("order") Order order, BindingResult result, Model model) {
        // Если есть ошибки валидации (включая невыбранное блюдо)
        if (result.hasErrors()) {
            // Возвращаем на форму, снова добавив список блюд
            model.addAttribute("dishes", menuService.findAll());
            return "order/new";
        }
        // Если все в порядке, Spring уже привязал блюдо. Сохраняем.
        orderService.save(order);
        // Редирект на основной список заказов
        return "redirect:/order";
    }

    @GetMapping("/edit/{id}")
    public String editOrderForm(@PathVariable Long id, Model model) {
        Order order = orderService.findById(id);
        // Простая проверка, можно оставить или заменить на обработку исключения
        if (order == null) {
            throw new IllegalArgumentException("Order not found with ID: " + id);
        }
        model.addAttribute("order", order);
        model.addAttribute("dishes", menuService.findAll());
        // Убедитесь, что ваш шаблон называется order/form.html
        return "order/form";
    }

    // --- ИСПРАВЛЕННЫЙ МЕТОД UPDATE ---
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

        // Устанавливаем ID из URL, чтобы обновить правильную запись
        order.setId(id);
        orderService.save(order);
        // Исправленный редирект
        return "redirect:/order";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
        return "redirect:/order";
    }
}