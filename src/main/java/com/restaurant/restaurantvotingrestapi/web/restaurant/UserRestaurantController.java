package com.restaurant.restaurantvotingrestapi.web.restaurant;

import com.restaurant.restaurantvotingrestapi.dto.RestaurantTo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Ivan Kurilov on 04.01.2021
 */

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@CacheConfig(cacheNames = "restaurant")
public class UserRestaurantController extends AbstractRestaurantController{
    public static final String REST_URL = "/rest/profile/restaurants";

    @GetMapping("/by")
    public List<RestaurantTo> getVotesByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return super.getAllVotesByDate(date);
    }

}
