// TODO: реализовать базовый функционал MenuService
package ru.ikm.restaurant.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ru.ikm.restaurant.entity.Menu;
import ru.ikm.restaurant.repository.MenuRepository;

import java.util.List;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    public Menu findById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Menu not found with id: " + id));
    }

    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }

    public void delete(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Menu not found with id: " + id));

        menu.getOrders().size();
        menuRepository.delete(menu);
    }
}
