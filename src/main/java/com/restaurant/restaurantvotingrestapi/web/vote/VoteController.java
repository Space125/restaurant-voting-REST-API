package com.restaurant.restaurantvotingrestapi.web.vote;

import com.restaurant.restaurantvotingrestapi.model.Vote;
import com.restaurant.restaurantvotingrestapi.web.AuthUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * @author Ivan Kurilov on 29.12.2020
 */
@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@CacheConfig(cacheNames = "restaurants")
public class VoteController extends AbstractVoteController {
    static final String REST_URL = "/rest/profile/votes";

    @PostMapping
    @Transactional
    @CacheEvict(allEntries = true)
    public ResponseEntity<Vote> create(@RequestParam int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        log.info("create or change vote for restaurant={}", restaurantId);
        Vote vote = createOrChange(authUser.id(), restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(vote.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(vote);
    }
}
