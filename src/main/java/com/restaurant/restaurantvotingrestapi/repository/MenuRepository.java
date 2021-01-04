package com.restaurant.restaurantvotingrestapi.repository;

import com.restaurant.restaurantvotingrestapi.model.Menu;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Ivan Kurilov on 30.12.2020
 */

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Menu m WHERE m.id=:id AND m.restaurant.id=:restaurantId")
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=:restaurantId ORDER BY m.dateMenu DESC")
    List<Menu> getAll(@Param("restaurantId") int restaurantId);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=:restaurantId AND m.dateMenu=:dateMenu ORDER BY m.name DESC")
    List<Menu> getByDate(@Param("restaurantId") int restaurantId, @Param("dateMenu") LocalDate date);

}
