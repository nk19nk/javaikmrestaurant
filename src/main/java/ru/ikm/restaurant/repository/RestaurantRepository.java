package ru.ikm.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ikm.restaurant.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
