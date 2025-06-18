// TODO: создать базовый JPA репозиторий для сущности Restaurant
package ru.ikm.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ikm.restaurant.entity.Restaurant;

/**
 * Базовый JPA-репозиторий для работы с сущностью {@link Restaurant}.
 * Предоставляет стандартные методы CRUD для взаимодействия с таблицей ресторанов.
 */
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
