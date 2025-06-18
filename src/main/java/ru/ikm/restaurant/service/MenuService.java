// TODO: реализовать базовый функционал MenuService
package ru.ikm.restaurant.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ru.ikm.restaurant.entity.Menu;
import ru.ikm.restaurant.repository.MenuRepository;

import java.util.List;

/**
 * Сервис для работы с сущностью {@link Menu}.
 * Предоставляет базовый функционал CRUD-операций над меню.
 */
@Service
public class MenuService {

    private final MenuRepository menuRepository;

    /**
     * Конструктор с внедрением зависимости репозитория.
     *
     * @param menuRepository Репозиторий для работы с данными меню
     */
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    /**
     * Возвращает список всех доступных блюд из меню.
     *
     * @return Список объектов типа {@link Menu}
     */
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    /**
     * Находит блюдо по его идентификатору.
     *
     * @param id Идентификатор блюда
     * @return Объект типа {@link Menu}, если найден
     * @throws EntityNotFoundException если блюдо не найдено
     */
    public Menu findById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Menu not found with id: " + id));
    }

    /**
     * Сохраняет или обновляет информацию о блюде.
     *
     * @param menu Объект меню для сохранения
     * @return Сохранённый объект типа {@link Menu}
     */
    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }

    /**
     * Удаляет блюдо по его идентификатору.
     * Также удаляет связанные заказы при каскадном удалении.
     *
     * @param id Идентификатор блюда
     * @throws EntityNotFoundException если блюдо не найдено
     */
    public void delete(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Menu not found with id: " + id));

        menu.getOrders().size();
        menuRepository.delete(menu);
    }
}
