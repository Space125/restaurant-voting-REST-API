package com.restaurant.restaurantvotingrestapi.web.vote;

import com.restaurant.restaurantvotingrestapi.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.restaurant.restaurantvotingrestapi.RestaurantTestData.RESTAURANT_1_ID;
import static com.restaurant.restaurantvotingrestapi.TestUtil.userHttpBasic;
import static com.restaurant.restaurantvotingrestapi.UserTestData.user;
import static com.restaurant.restaurantvotingrestapi.web.vote.VoteController.REST_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Ivan Kurilov on 11.01.2021
 */

public class VoteRestControllerTest extends AbstractControllerTest {

    @Test
    void voteSimple() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT_1_ID))
                .with(userHttpBasic(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
