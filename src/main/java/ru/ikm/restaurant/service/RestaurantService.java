// TODO: реализовать базовый функционал RestaurantService
package ru.ikm.restaurant.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ru.ikm.restaurant.entity.Restaurant;
import ru.ikm.restaurant.repository.RestaurantRepository;

import java.util.List;

/**
 * Сервис для работы с сущностью {@link Restaurant}.
 * Предоставляет базовый функционал CRUD-операций над ресторанами.
 */
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    /**
     * Конструктор с внедрением зависимости репозитория.
     *
     * @param restaurantRepository Репозиторий для работы с данными ресторанов
     */
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    /**
     * Возвращает список всех доступных ресторанов.
     *
     * @return Список объектов типа {@link Restaurant}
     */
    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    /**
     * Находит ресторан по его идентификатору.
     *
     * @param id Идентификатор ресторана
     * @return Объект типа {@link Restaurant}, если найден, иначе null
     */
    public Restaurant findById(Long id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    /**
     * Проверяет, существует ли ресторан с указанным идентификатором.
     *
     * @param id Идентификатор ресторана
     * @return true, если ресторан существует, иначе false
     */
    public boolean existsById(Long id) {
        return restaurantRepository.existsById(id);
    }

    /**
     * Сохраняет или обновляет информацию о ресторане.
     *
     * @param restaurant Объект ресторана для сохранения
     * @return Сохранённый объект типа {@link Restaurant}
     */
    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    /**
     * Удаляет ресторан по его идентификатору.
     * Также удаляются связанные блюда при каскадном удалении.
     *
     * @param id Идентификатор ресторана
     * @throws EntityNotFoundException если ресторан не найден
     */
    public void delete(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Restaurant not found with id: " + id));
        restaurant.getMenuList().size();
        restaurantRepository.delete(restaurant);
    }

}

