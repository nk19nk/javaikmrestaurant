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

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final MenuService menuService;

    public OrderController(OrderService orderService,
                           MenuService menuService) {
        this.orderService = orderService;
        this.menuService = menuService;
    }

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute(
                "orders", orderService.findAll()
        );
        return "order/list";
    }

    @GetMapping("/new")
    public String newOrderForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("dishes", menuService.findAll());
        return "order/new";
    }

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

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
        return "redirect:/order";
    }
}