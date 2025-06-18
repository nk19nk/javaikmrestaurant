// TODO: создать базовый JPA репозиторий для сущности Order
package ru.ikm.restaurant.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.ikm.restaurant.entity.Order;

/**
 * Базовый JPA-репозиторий для работы с сущностью {@link Order}.
 * Предоставляет стандартные методы CRUD для взаимодействия с таблицей заказов.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
