package com.restaurant.restaurantvotingrestapi.web.restaurant;

import com.restaurant.restaurantvotingrestapi.model.Menu;
import com.restaurant.restaurantvotingrestapi.model.Restaurant;
import com.restaurant.restaurantvotingrestapi.repository.MenuRepository;
import com.restaurant.restaurantvotingrestapi.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import static com.restaurant.restaurantvotingrestapi.util.ValidationUtil.checkNotFoundWithId;
import static com.restaurant.restaurantvotingrestapi.util.ValidationUtil.checkSingleModification;

/**
 * @author Ivan Kurilov on 30.12.2020
 */

@Slf4j
public abstract class AbstractRestaurantController {

    @Autowired
    protected RestaurantRepository restaurantRepository;

    @Autowired
    protected MenuRepository menuRepository;

    public Restaurant get(int id) {
        log.info("get restaurant={}", id);
        return checkNotFoundWithId(restaurantRepository.findById(id), id);
    }

    public void deleteRestaurant(int id) {
        log.info("deleted restaurant={}", id);
        checkSingleModification(restaurantRepository.delete(id), "Restaurant id=" + id + " not found");
    }

    public void updateRestaurant(Restaurant restaurant, int id) {
        log.info("update restaurant {}", restaurant);
        checkNotFoundWithId(restaurantRepository.findById(id), "Restaurant id=" + id + " not found");
        restaurantRepository.save(restaurant);

    }

    public Menu getMenu(int id, int restaurantId) {
        log.info("get menu={} for restaurant={}", id, restaurantId);
        return checkNotFoundWithId(menuRepository.findById(id), restaurantId);
    }

    public void updateMenu(Menu menu, int restaurantId, int menuId) {
        checkNotFoundWithId(menuRepository.findById(menuId), restaurantId);
        menu.setRestaurant(restaurantRepository.getExisted(restaurantId));
        menuRepository.save(menu);
    }

    public void deleteMenu(int id, int restaurantId) {
        log.info("deleted menu={} for restaurant={}", id, restaurantId);
        checkSingleModification(menuRepository.delete(id, restaurantId), "Menu id=" + id + " not found");
    }
}
