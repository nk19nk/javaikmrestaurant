// TODO: создать сущность Restaurant
package ru.ikm.restaurant.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "Restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название ресторана не может быть пустым")
    @Pattern(
            regexp = "^[А-Яа-яA-Za-z\\s]{2,50}$",
            message = "Название ресторана должно содержать " +
                    "только буквы и пробелы (от 2 до 50 символов)")
    private String name;

    @NotBlank(message = "Адрес не может быть пустым")
    @Pattern(
            regexp = "^[А-Яа-яA-Za-z0-9\\s,.-]{2,100}$",
            message = "Адрес должен содержать только допустимые символы (от 2 до 100)")
    @Pattern(
            regexp = ".*[А-Яа-яA-Za-z].*",
            message = "Адрес не может состоять только из цифр и символов")
    private String location;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menuList = new ArrayList<>();

}
