package com.restaurant.restaurantvotingrestapi.repository;

import com.restaurant.restaurantvotingrestapi.model.User;
import com.restaurant.restaurantvotingrestapi.model.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Ivan Kurilov on 29.12.2020
 */

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId ORDER BY v.dateVote DESC")
    List<Vote> getAll(@Param("userId") int userId);

    @Query("SELECT v FROM Vote v WHERE v.user=:user AND v.dateVote=:dateVote")
    Optional<Vote> getByUserAndDateVote(User user, LocalDate dateVote);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.dateVote=:dateVote")
    Optional<Vote> getByUserIdAndDateVote(@Param("userId") int userId, @Param("dateVote") LocalDate dateVote);
}
