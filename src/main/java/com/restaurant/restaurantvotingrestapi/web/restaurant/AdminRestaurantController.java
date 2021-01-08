package com.restaurant.restaurantvotingrestapi.web.restaurant;

import com.restaurant.restaurantvotingrestapi.model.Menu;
import com.restaurant.restaurantvotingrestapi.model.Restaurant;
import com.restaurant.restaurantvotingrestapi.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.restaurant.restaurantvotingrestapi.web.restaurant.AdminRestaurantController.REST_URL;

/**
 * @author Ivan Kurilov on 30.12.2020
 */
@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "restaurants")
public class AdminRestaurantController extends AbstractRestaurantController {
    public final static String REST_URL = "/rest/admin/restaurants";
    public final static String MENU_REST_URL = "/{restaurantId}/menus";

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return super.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void delete(@PathVariable int id) {
        super.deleteRestaurant(id);

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    @Transactional
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody Restaurant restaurant) {
        ValidationUtil.checkNew(restaurant);
        log.info("create restaurant {}", restaurant);
        Restaurant created = restaurantRepository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Transactional
    public void updateRestaurant(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        ValidationUtil.assureIdConsistent(restaurant, id);
        super.updateRestaurant(restaurant, id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict(allEntries = true)
    public void enable(@PathVariable int id, @RequestParam boolean enabled) {
        log.info(enabled ? "enabled {}" : "disabled {}", id);
        Restaurant restaurant = restaurantRepository.getExisted(id);
        restaurant.setEnabled(enabled);
    }

    @GetMapping(MENU_REST_URL)
    public List<Menu> getAllMenus(@PathVariable int restaurantId) {
        log.info("getAllMenus for restaurant={}", restaurantId);
        return menuRepository.getAll(restaurantId);
    }

    @GetMapping(MENU_REST_URL + "/{menuId}")
    public Menu getMenu(@PathVariable int restaurantId, @PathVariable int menuId) {
        return super.getMenu(menuId, restaurantId);
    }

    @PostMapping(value = MENU_REST_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    @Transactional
    public ResponseEntity<Menu> createMenu(@PathVariable int restaurantId, @Valid @RequestBody Menu menu) {
        ValidationUtil.checkNew(menu);
        log.info("create menu {} for restaurant {}", menu, restaurantId);
        menu.setRestaurant(restaurantRepository.getExisted(restaurantId));
        Menu created = menuRepository.save(menu);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + MENU_REST_URL + "/{menuId}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = MENU_REST_URL + "/{menuId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void updateMenu(@PathVariable int restaurantId, @PathVariable int menuId, @Valid @RequestBody Menu menu) {
        log.info("update menu {} for restaurant {}", menu, restaurantId);
        ValidationUtil.assureIdConsistent(menu, menuId);
        super.updateMenu(menu, restaurantId, menuId);
    }

    @DeleteMapping(MENU_REST_URL + "/{menuId}")
    @CacheEvict(allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenu(@PathVariable int restaurantId, @PathVariable int menuId) {
        super.deleteMenu(menuId, restaurantId);
    }
}
