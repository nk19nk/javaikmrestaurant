// TODO: реализовать базовый функционал RestaurantService
package ru.ikm.restaurant.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ru.ikm.restaurant.entity.Restaurant;
import ru.ikm.restaurant.repository.RestaurantRepository;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    public Restaurant findById(Long id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    public boolean existsById(Long id) {
        return restaurantRepository.existsById(id);
    }

    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public void delete(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Restaurant not found with id: " + id));
        restaurant.getMenuList().size();
        restaurantRepository.delete(restaurant);
    }

}

