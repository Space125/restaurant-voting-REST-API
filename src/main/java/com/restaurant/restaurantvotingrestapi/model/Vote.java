package com.restaurant.restaurantvotingrestapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author Ivan Kurilov on 25.12.2020
 */

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_vote"}, name="vote_unique_user_date_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Vote extends AbstractBaseEntity{

    @Column(name = "date_vote", nullable = false)
    @NonNull
    private LocalDate dateVote = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NonNull
    @JsonBackReference(value = "userVotes")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NonNull
    @JsonBackReference("restaurantVotes")
    private Restaurant restaurant;

    public Vote(LocalDate localDate, User user, Restaurant restaurant) {
        this(null, localDate, user, restaurant);
    }

    public Vote(Integer id, LocalDate localDate, User user, Restaurant restaurant) {
        super(id);
        this.dateVote = localDate;
        this.user = user;
        this.restaurant = restaurant;
    }
}
