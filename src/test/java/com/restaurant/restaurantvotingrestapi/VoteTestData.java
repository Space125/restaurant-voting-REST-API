package com.restaurant.restaurantvotingrestapi;

import com.restaurant.restaurantvotingrestapi.model.Vote;

import java.time.LocalDate;
import java.util.List;

import static com.restaurant.restaurantvotingrestapi.RestaurantTestData.restaurant_1;
import static com.restaurant.restaurantvotingrestapi.RestaurantTestData.restaurant_3;
import static com.restaurant.restaurantvotingrestapi.UserTestData.*;
import static com.restaurant.restaurantvotingrestapi.model.AbstractBaseEntity.START_SEQ;

/**
 * @author Ivan Kurilov on 11.01.2021
 */
public class VoteTestData {
    public static TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingEqualsComparator(Vote.class);
    public static TestMatcher<Vote> VOTE_LAZY_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Vote.class, "user", "restaurant");

    public static final int VOTE_1_ID = START_SEQ + 7;
    public static final int VOTE_2_ID = START_SEQ + 8;
    public static final int VOTE_3_ID = START_SEQ + 9;

    public static final Vote vote_1 = new Vote(VOTE_1_ID, LocalDate.of(2020, 12, 23), user, restaurant_1);
    public static final Vote vote_2 = new Vote(VOTE_2_ID, LocalDate.of(2020, 12, 24), user, restaurant_1);
    public static final Vote vote_3 = new Vote(VOTE_3_ID, LocalDate.of(2020, 12, 25), user, restaurant_3);

    public static final List<Vote> votes = List.of(vote_3, vote_2, vote_1);

    public static Vote getNewVote() {
        return new Vote(null, LocalDate.now(), user, restaurant_1);
    }
}
