package ru.ikm.restaurant.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "Orders")
public class Order {
    // Геттеры и сеттеры
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dish_id", nullable = false)
    @NotNull(message = "Блюдо обязательно для указания")
    private Menu dish;

    @Min(value = 1, message = "Количество должно быть не меньше 1")
    private int quantity;

}
