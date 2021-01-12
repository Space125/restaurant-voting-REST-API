package com.restaurant.restaurantvotingrestapi;

import com.restaurant.restaurantvotingrestapi.model.Menu;

import java.time.LocalDate;

import static com.restaurant.restaurantvotingrestapi.RestaurantTestData.restaurant_1;
import static com.restaurant.restaurantvotingrestapi.model.AbstractBaseEntity.START_SEQ;

/**
 * @author Ivan Kurilov on 12.01.2021
 */
public class MenuTestData {
    public static TestMatcher<Menu> MENU_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Menu.class, "restaurant");

    public static final LocalDate MENU_TEST_DATE = LocalDate.of(2020,12,23);
    public static final int MENU_1_ID = START_SEQ + 18;
    public static final int MENU_2_ID = START_SEQ + 19;
    public static final int MENU_3_ID = START_SEQ + 20;

    public static final Menu menu_1 = new Menu(MENU_1_ID,  "Caprese Burger", 500L, MENU_TEST_DATE, restaurant_1);
    public static final Menu menu_2 = new Menu(MENU_2_ID,  "Borsch", 300L, MENU_TEST_DATE, restaurant_1);
    public static final Menu menu_3 = new Menu(MENU_3_ID,  "New York Cheesecake", 350L, MENU_TEST_DATE, restaurant_1);

    public static Menu getNewMenu() {
        return new Menu(null, "New", 1000L, LocalDate.now(), restaurant_1);
    }

    public static Menu getUpdatedMenu() {
        Menu updated = new Menu(menu_1);
        updated.setName("UpdatedName");
        return updated;
    }
}
