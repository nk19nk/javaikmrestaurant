// TODO: создать сущность Menu
package ru.ikm.restaurant.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Сущность, представляющая блюдо в меню ресторана.
 * Связана с таблицей "Menu" в базе данных.
 * Содержит информацию о названии блюда, цене и ссылке на ресторан.
 */
@Setter
@Getter
@Entity
@Table(name = "Menu")
public class Menu {
    /**
     * Уникальный идентификатор блюда.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Ресторан, к которому относится это блюдо.
     * Поле не может быть null.
     */
    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull(message = "Ресторан обязателен для указания")
    private Restaurant restaurant;

    /**
     * Название блюда.
     * Должно содержать только буквы и пробелы, длина от 2 до 50 символов.
     * Поле не может быть пустым.
     */
    @NotBlank(message = "Название блюда не может быть пустым")
    @Pattern(
            regexp = "^[А-Яа-яA-Za-z\\s]{2,50}$",
            message = "Название блюда" +
                    " должно содержать только буквы и пробелы (от 2 до 50 символов)")
    private String dishName;

    /**
     * Цена блюда.
     * Должна быть больше 0.00.
     * Поле не может быть null.
     */
    @NotNull(message = "Цена обязательна для указания")
    @DecimalMin(value = "0.01", message = "Цена должна быть больше 0")
    private BigDecimal price;

    /**
     * Список заказов, связанных с этим блюдом.
     * Используется каскадное удаление при удалении блюда.
     */
    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

}
