package com.restaurant.restaurantvotingrestapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.checkerframework.checker.index.qual.Positive;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author Ivan Kurilov on 25.12.2020
 */

@Entity
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "name", "date_menu"}, name = "dish_unique_restaurant_name_date_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Menu extends AbstractNamedEntity implements HasId {

    @Column(name = "price", nullable = false)
    @Positive
    private Long price;

    @Column(name = "date_menu", nullable = false)
    @NonNull
    private LocalDate dateMenu = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NonNull
    @JsonBackReference(value = "restaurantMenus")
    private Restaurant restaurant;

    public Menu(Integer id, String name, @Positive Long price, @NonNull LocalDate dateMenu, @NonNull Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.dateMenu = dateMenu;
        this.restaurant = restaurant;
    }

    public Menu(Menu menu){
        this(menu.getId(), menu.getName(), menu.getPrice(), menu.getDateMenu(), menu.getRestaurant());
    }
}
