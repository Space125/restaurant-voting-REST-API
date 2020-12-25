package com.restaurant.restaurantvotingrestapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @author Ivan Kurilov on 25.12.2020
 */

@Entity
@Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurant_unique_name_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Restaurant extends AbstractNamedEntity implements HasId {

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()")
    @NonNull
    private Date registered = new Date();

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    private boolean enabled = true;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("dateVote DESC")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference(value = "restaurantVotes")
    private Set<Vote> votes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("dateMenu DESC")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference(value = "restaurantVotes")
    private Set<Menu> menus;

    public Restaurant(Integer id, String name, boolean enabled, Date registered) {
        super(id, name);
        this.enabled = enabled;
        this.registered = registered;
    }

    public Restaurant(Integer id, String name) {
        this(id, name, true, new Date());
    }

    public Restaurant(Restaurant restaurant) {
        this(restaurant.getId(), restaurant.getName(), restaurant.isEnabled(), restaurant.getRegistered());
    }
}
