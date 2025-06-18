// TODO: создать базовый JPA репозиторий для сущности Menu
package ru.ikm.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ikm.restaurant.entity.Menu;

/**
 * Базовый JPA-репозиторий для работы с сущностью {@link Menu}.
 * Предоставляет стандартные методы CRUD для взаимодействия с таблицей меню.
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {
}
