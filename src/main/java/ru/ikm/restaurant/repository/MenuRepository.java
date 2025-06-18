// TODO: создать базовый JPA репозиторий для сущности Menu
package ru.ikm.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ikm.restaurant.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
