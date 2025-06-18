package ru.ikm.restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основной класс приложения для управления рестораном.
 * Запускает Spring Boot-приложение.
 *
 * <p>Содержит точку входа (main-метод) и аннотирован как {@link SpringBootApplication},
 * что позволяет Spring автоматически настроить компоненты, репозитории и контроллеры.</p>
 */
@SpringBootApplication
public class RestaurantApplication {

    /**
     * Точка входа в приложение.
     *
     * @param args Аргументы командной строки
     */
    public static void main(String[] args) {
        SpringApplication.run(RestaurantApplication.class, args);
    }

}
