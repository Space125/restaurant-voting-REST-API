package com.restaurant.restaurantvotingrestapi.web.restaurant;

import com.restaurant.restaurantvotingrestapi.dto.RestaurantTo;
import com.restaurant.restaurantvotingrestapi.model.Restaurant;
import com.restaurant.restaurantvotingrestapi.repository.MenuRepository;
import com.restaurant.restaurantvotingrestapi.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

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

    public Restaurant get(int id){
        log.info("get restaurant={}", id);
        return checkNotFoundWithId(restaurantRepository.findById(id), id);
    }

    public void delete(int id){
        log.info("deleted {}", id);
        checkSingleModification(restaurantRepository.delete(id), "Restaurant id=" + id + "not found");
    }

    public List<RestaurantTo> getAllVotesByDate(LocalDate date){
        log.info("get all votes by date={}", date);
        return restaurantRepository.getAllByDate(date);
    }

    public Restaurant create(Restaurant restaurant){
        log.info("create restaurant {}", restaurant);
        return restaurantRepository.save(restaurant);
    }

    public void update(Restaurant restaurant, int id){
        log.info("update restaurant {}", restaurant);
        checkNotFoundWithId(restaurantRepository.findById(id), "Restaurant id=" + id + "not found");
        restaurantRepository.save(restaurant);

    }


}
