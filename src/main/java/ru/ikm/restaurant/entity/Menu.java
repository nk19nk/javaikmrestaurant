package ru.ikm.restaurant.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "Menu")
public class Menu {
    // Геттеры и сеттеры
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull(message = "Ресторан обязателен для указания")
    private Restaurant restaurant;

    @NotBlank(message = "Название блюда не может быть пустым")
    @Pattern(regexp = "^[А-Яа-яA-Za-z\\s]{2,50}$", message = "Название блюда должно содержать только буквы и пробелы (от 2 до 50 символов)")
    private String dishName;

    @NotNull(message = "Цена обязательна для указания")
    @DecimalMin(value = "0.01", message = "Цена должна быть больше 0")
    private BigDecimal price;

    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

}
