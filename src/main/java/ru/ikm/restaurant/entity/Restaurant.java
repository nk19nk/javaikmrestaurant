// TODO: создать сущность Restaurant
package ru.ikm.restaurant.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Сущность, представляющая ресторан.
 * Связана с таблицей "Restaurants" в базе данных.
 * Содержит информацию о названии, адресе и меню ресторана.
 */
@Setter
@Getter
@Entity
@Table(name = "Restaurants")
public class Restaurant {
    /**
     * Уникальный идентификатор ресторана.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название ресторана.
     * Должно содержать только буквы и пробелы, длина от 2 до 50 символов.
     * Поле не может быть пустым.
     */
    @NotBlank(message = "Название ресторана не может быть пустым")
    @Pattern(
            regexp = "^[А-Яа-яA-Za-z\\s]{2,50}$",
            message = "Название ресторана должно содержать " +
                    "только буквы и пробелы (от 2 до 50 символов)")
    private String name;

    /**
     * Адрес ресторана.
     * Должен содержать допустимые символы, длина от 2 до 100 символов.
     * Не может состоять только из цифр и спецсимволов.
     * Поле не может быть пустым.
     */
    @NotBlank(message = "Адрес не может быть пустым")
    @Pattern(
            regexp = "^[А-Яа-яA-Za-z0-9\\s,.-]{2,100}$",
            message = "Адрес должен содержать только допустимые символы (от 2 до 100)")
    @Pattern(
            regexp = ".*[А-Яа-яA-Za-z].*",
            message = "Адрес не может состоять только из цифр и символов")
    private String location;

    /**
     * Список блюд, входящих в меню ресторана.
     * Используется каскадное удаление при удалении ресторана.
     */
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menuList = new ArrayList<>();

}
