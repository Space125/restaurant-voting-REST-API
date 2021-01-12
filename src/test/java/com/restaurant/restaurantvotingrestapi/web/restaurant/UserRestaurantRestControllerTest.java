package com.restaurant.restaurantvotingrestapi.web.restaurant;

import com.restaurant.restaurantvotingrestapi.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.restaurant.restaurantvotingrestapi.MenuTestData.menu_1;
import static com.restaurant.restaurantvotingrestapi.RestaurantTestData.restaurant_1;
import static com.restaurant.restaurantvotingrestapi.TestUtil.userHttpBasic;
import static com.restaurant.restaurantvotingrestapi.UserTestData.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Ivan Kurilov on 12.01.2021
 */

class UserRestaurantRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = UserRestaurantController.REST_URL + '/';
    private static final String FULL_MENU_REST_URL = UserRestaurantController.REST_URL + UserRestaurantController.MENU_REST_URL + '/';

    @Test
    @WithAnonymousUser
    void getAllRestaurantsByToday() throws Exception {
        perform(MockMvcRequestBuilders.get(UserRestaurantController.REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getMenuByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(FULL_MENU_REST_URL + "by?date=" + menu_1.getDateMenu(), restaurant_1.id())
                .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getOneMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(FULL_MENU_REST_URL + menu_1.id(), restaurant_1.id())
                .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getRestaurantsByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "by?date=" + menu_1.getDateMenu())
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
