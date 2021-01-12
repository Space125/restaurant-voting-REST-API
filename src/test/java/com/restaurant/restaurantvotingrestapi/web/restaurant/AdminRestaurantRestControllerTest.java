package com.restaurant.restaurantvotingrestapi.web.restaurant;

import com.restaurant.restaurantvotingrestapi.exception.NotFoundException;
import com.restaurant.restaurantvotingrestapi.model.Menu;
import com.restaurant.restaurantvotingrestapi.model.Restaurant;
import com.restaurant.restaurantvotingrestapi.repository.MenuRepository;
import com.restaurant.restaurantvotingrestapi.repository.RestaurantRepository;
import com.restaurant.restaurantvotingrestapi.util.JsonUtil;
import com.restaurant.restaurantvotingrestapi.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.restaurant.restaurantvotingrestapi.MenuTestData.*;
import static com.restaurant.restaurantvotingrestapi.RestaurantTestData.*;
import static com.restaurant.restaurantvotingrestapi.TestUtil.readFromJson;
import static com.restaurant.restaurantvotingrestapi.TestUtil.userHttpBasic;
import static com.restaurant.restaurantvotingrestapi.UserTestData.admin;
import static com.restaurant.restaurantvotingrestapi.UserTestData.user;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Ivan Kurilov on 12.01.2021
 */

class AdminRestaurantRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = AdminRestaurantController.REST_URL + '/';
    private static final String FULL_MENU_REST_URL = AdminRestaurantController.REST_URL + AdminRestaurantController.MENU_REST_URL + '/';

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_1_ID)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurant_1));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_1_ID))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_1_ID)
                .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT_1_ID)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> restaurantRepository.getExisted(RESTAURANT_1_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void create() throws Exception {
        Restaurant newRestaurant = getNewRestaurant();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andDo(print())
                .andExpect(status().isCreated());

        Restaurant created = readFromJson(action, Restaurant.class);
        int newId = created.id();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(restaurantRepository.getExisted(newId), newRestaurant);
    }

    @Test
    void update() throws Exception {
        Restaurant updated = getUpdatedRestaurant();
        perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        RESTAURANT_MATCHER.assertMatch(restaurantRepository.getExisted(RESTAURANT_1_ID), updated);
    }

    @Test
    void enable() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL + RESTAURANT_1_ID)
                .param("enabled", "false")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertFalse(restaurantRepository.getExisted(RESTAURANT_1_ID).isEnabled());
    }

    @Test
    void getOneMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(FULL_MENU_REST_URL + MENU_1_ID, RESTAURANT_1_ID)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(menu_1));
    }

    @Test
    void getAllMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(FULL_MENU_REST_URL, RESTAURANT_1_ID)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void createMenu() throws Exception {
        Menu newDish = getNewMenu();
        ResultActions action = perform(MockMvcRequestBuilders.post(FULL_MENU_REST_URL, RESTAURANT_1_ID)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isCreated());

        Menu created = readFromJson(action, Menu.class);
        int newId = created.id();
        newDish.setId(newId);
        MENU_MATCHER.assertMatch(created, newDish);
        MENU_MATCHER.assertMatch(menuRepository.getExisted(newId), newDish);
    }

    @Test
    void updateMenu() throws Exception {
        Menu updated = getUpdatedMenu();
        perform(MockMvcRequestBuilders.put(FULL_MENU_REST_URL + MENU_1_ID, RESTAURANT_1_ID)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        MENU_MATCHER.assertMatch(menuRepository.getExisted(MENU_1_ID), updated);
    }

    @Test
    void deleteMenu() throws Exception {
        perform(MockMvcRequestBuilders.delete(FULL_MENU_REST_URL + MENU_1_ID, RESTAURANT_1_ID)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> menuRepository.getExisted(MENU_1_ID));
    }
}
