package com.restaurant.restaurantvotingrestapi.web.user;

import com.restaurant.restaurantvotingrestapi.dto.UserTo;
import com.restaurant.restaurantvotingrestapi.model.User;
import com.restaurant.restaurantvotingrestapi.model.Vote;
import com.restaurant.restaurantvotingrestapi.util.UserUtil;
import com.restaurant.restaurantvotingrestapi.util.ValidationUtil;
import com.restaurant.restaurantvotingrestapi.web.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Ivan Kurilov on 29.12.2020
 */

@RestController
@RequestMapping(value = ProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "users")
public class ProfileRestController extends AbstractUserController {
    public static final String REST_URL = "/rest/profile";

    @GetMapping
    public HttpEntity<User> get(@AuthenticationPrincipal AuthUser authUser) {
        return super.get(authUser.id());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        super.delete(authUser.id());
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(allEntries = true)
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        log.info("register {}", userTo);
        ValidationUtil.checkNew(userTo);
        User created = prepareToSave(UserUtil.createNewFromTo(userTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict(allEntries = true)
    public void update(@RequestBody UserTo userTo, @AuthenticationPrincipal AuthUser authUser) throws BindException {
        validateBeforeUpdate(userTo, authUser.id());
        User user = userRepository.getExisted(userTo.id());
        prepareToSave(UserUtil.updateFromTo(user, userTo));
        log.info("update user={}", authUser.id());
    }

    @GetMapping("/with-votes")
    public ResponseEntity<User> getWithVotes(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get user={} profile with votes", authUser.id());
        return ResponseEntity.of(userRepository.getWithVotes(authUser.id()));
    }

    @GetMapping("/votes")
    public List<Vote> getAllVotes(@AuthenticationPrincipal AuthUser authUser) {
        return super.getAllVotes(authUser.id());
    }

    @GetMapping("/votes/by")
    public Vote getVoteByDate(@AuthenticationPrincipal AuthUser authUser,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return super.getVoteByDate(authUser.id(), date);
    }

    @GetMapping("/votes/today")
    public Vote getVoteToday(@AuthenticationPrincipal AuthUser authUser) {
        return super.getVoteToday(authUser.id());
    }
}
