package ru.ikm.restaurant.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.ikm.restaurant.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
