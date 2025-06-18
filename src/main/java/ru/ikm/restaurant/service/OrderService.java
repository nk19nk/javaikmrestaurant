// TODO: реализовать базовый функционал OrderService
package ru.ikm.restaurant.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ru.ikm.restaurant.entity.Order;
import ru.ikm.restaurant.repository.OrderRepository;

import java.util.List;

/**
 * Сервис для работы с сущностью {@link Order}.
 * Предоставляет базовый функционал CRUD-операций над заказами.
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    /**
     * Конструктор с внедрением зависимости репозитория.
     *
     * @param orderRepository Репозиторий для работы с данными заказов
     */
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Возвращает список всех заказов.
     *
     * @return Список объектов типа {@link Order}
     */
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    /**
     * Находит заказ по его идентификатору.
     *
     * @param id Идентификатор заказа
     * @return Объект типа {@link Order}, если найден
     * @throws EntityNotFoundException если заказ не найден
     */
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Order not found with id: " + id));
    }

    /**
     * Сохраняет или обновляет информацию о заказе.
     *
     * @param order Объект заказа для сохранения
     * @return Сохранённый объект типа {@link Order}
     */
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    /**
     * Удаляет заказ по его идентификатору.
     *
     * @param id Идентификатор заказа
     */
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}


