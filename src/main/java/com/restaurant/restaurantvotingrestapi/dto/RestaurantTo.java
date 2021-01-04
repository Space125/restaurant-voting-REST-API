package com.restaurant.restaurantvotingrestapi.dto;

import com.restaurant.restaurantvotingrestapi.model.HasId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * @author Ivan Kurilov on 04.01.2021
 */

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantTo extends BaseTo implements HasId {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @Positive
    private long rating;

    public RestaurantTo(Integer id, String name, long rating) {
        super(id);
        this.name = name;
        this.rating = rating;
    }
}
