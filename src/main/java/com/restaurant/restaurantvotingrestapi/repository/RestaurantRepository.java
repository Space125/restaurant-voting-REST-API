package com.restaurant.restaurantvotingrestapi.repository;

import com.restaurant.restaurantvotingrestapi.dto.RestaurantTo;
import com.restaurant.restaurantvotingrestapi.model.Restaurant;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Ivan Kurilov on 29.12.2020
 */

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

    //https://stackoverflow.com/questions/46083329/no-converter-found-capable-of-converting-from-type-to-type
    @Query("SELECT new com.restaurant.restaurantvotingrestapi.dto.RestaurantTo(r.id, r.name, count(v.restaurant.id))" +
            " FROM Restaurant r LEFT JOIN Vote v" +
            " ON r.id=v.restaurant.id AND v.dateVote=:date GROUP BY r.id ORDER BY v.restaurant.id DESC, r.name ASC")
    List<RestaurantTo> getAllByDate(@Param("date") LocalDate date);
}
