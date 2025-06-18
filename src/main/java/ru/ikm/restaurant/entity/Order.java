// TODO: создать сущность Order
package ru.ikm.restaurant.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Сущность, представляющая заказ на блюдо.
 * Связана с таблицей "Orders" в базе данных.
 * Содержит информацию о блюде и количестве, указанном в заказе.
 */
@Setter
@Getter
@Entity
@Table(name = "Orders")
public class Order {
    /**
     * Уникальный идентификатор заказа.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Блюдо, на которое сделан заказ.
     * Поле не может быть null.
     */
    @ManyToOne
    @JoinColumn(name = "dish_id", nullable = false)
    @NotNull(message = "Блюдо обязательно для указания")
    private Menu dish;

    /**
     * Количество заказанных порций блюда.
     * Должно быть не меньше 1.
     */
    @Min(value = 1, message = "Количество должно быть не меньше 1")
    private int quantity;

}
