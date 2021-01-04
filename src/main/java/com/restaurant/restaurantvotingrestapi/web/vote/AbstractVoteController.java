package com.restaurant.restaurantvotingrestapi.web.vote;

import com.restaurant.restaurantvotingrestapi.exception.NotFoundException;
import com.restaurant.restaurantvotingrestapi.exception.VoteDeadlineException;
import com.restaurant.restaurantvotingrestapi.model.Restaurant;
import com.restaurant.restaurantvotingrestapi.model.User;
import com.restaurant.restaurantvotingrestapi.model.Vote;
import com.restaurant.restaurantvotingrestapi.repository.RestaurantRepository;
import com.restaurant.restaurantvotingrestapi.repository.UserRepository;
import com.restaurant.restaurantvotingrestapi.repository.VoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.restaurant.restaurantvotingrestapi.util.ValidationUtil.checkNotFoundWithId;

/**
 * @author Ivan Kurilov on 29.12.2020
 */

@Slf4j
public abstract class AbstractVoteController {
    private static final LocalTime VOTE_DEADLINE = LocalTime.of(11, 0);

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public Vote createOrChange(int userId, int restaurantId) {
        LocalDateTime ldt = LocalDateTime.now(Clock.systemDefaultZone());
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.findById(restaurantId), restaurantId);
        User user = checkNotFoundWithId(userRepository.findById(userId), userId);
        Vote vote;
        try {
            vote = checkNotFoundWithId(voteRepository.getByUserAndDateVote(user, ldt.toLocalDate()), user.id());
        } catch (NotFoundException e) {
            log.debug("new vote from user={} for restaurant={}", userId, restaurantId);
            return voteRepository.save(new Vote(null, ldt.toLocalDate(), user, restaurant));
        }

        if (ldt.toLocalTime().isBefore(VOTE_DEADLINE)) {
            if (vote.getRestaurant().id() != restaurantId) {
                vote.setRestaurant(restaurant);
                log.debug("vote from user={} for restaurant={} was changed", userId, restaurantId);
                return vote;
            }
            log.debug("vote from user={} for restaurant={} not changed", userId, restaurantId);
            return vote;
        } else {
            throw new VoteDeadlineException("Vote deadline already passed");
        }
    }
}
