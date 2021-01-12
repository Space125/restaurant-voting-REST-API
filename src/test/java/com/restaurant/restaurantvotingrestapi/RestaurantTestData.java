package com.restaurant.restaurantvotingrestapi;

import com.restaurant.restaurantvotingrestapi.model.Restaurant;

import static com.restaurant.restaurantvotingrestapi.model.AbstractBaseEntity.START_SEQ;

/**
 * @author Ivan Kurilov on 11.01.2021
 */

public class RestaurantTestData {
    public static TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Restaurant.class, "registered", "menus", "votes");

    public static final int NOT_FOUND = 10;
    public static final int RESTAURANT_1_ID = START_SEQ + 3;
    public static final int RESTAURANT_2_ID = START_SEQ + 4;
    public static final int RESTAURANT_3_ID = START_SEQ + 5;
    public static final int RESTAURANT_4_ID = START_SEQ + 6;

    public static final Restaurant restaurant_1 = new Restaurant(RESTAURANT_1_ID, "Corner Grill");
    public static final Restaurant restaurant_2 = new Restaurant(RESTAURANT_2_ID, "Bluefin");
    public static final Restaurant restaurant_3 = new Restaurant(RESTAURANT_3_ID, "McDonalds");
    public static final Restaurant restaurant_4 = new Restaurant(RESTAURANT_4_ID, "Caesars Palace");

    public static Restaurant getNewRestaurant() {
        return new Restaurant(null, "NewRestaurant");
    }

    public static Restaurant getUpdatedRestaurant() {
        Restaurant updated = new Restaurant(restaurant_1);
        updated.setName("UpdatedName");
        return updated;
    }
}
