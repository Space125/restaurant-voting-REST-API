package com.restaurant.restaurantvotingrestapi.web.user;

import com.restaurant.restaurantvotingrestapi.model.User;
import com.restaurant.restaurantvotingrestapi.model.Vote;
import com.restaurant.restaurantvotingrestapi.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Ivan Kurilov on 28.12.2020
 */
@RestController
@RequestMapping(value = AdminUserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "users")
public class AdminUserController extends AbstractUserController {
    static final String REST_URL = "/rest/admin/users";

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @GetMapping("/{id}/with-votes")
    public ResponseEntity<User> getWithVotes(@PathVariable int id) {
        return super.getWithVotes(id);
    }

    @GetMapping
    @Cacheable
    public List<User> getAll() {
        log.info("getAll");
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "name", "email"));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    public ResponseEntity<User> createWithLocation(@Valid @RequestBody User user) {
        log.info("create {}", user);
        ValidationUtil.checkNew(user);
        User created = prepareToSave(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user, @PathVariable int id) throws BindException {
        validateBeforeUpdate(user, id);
        log.info("update {} with id={}", user, id);
        prepareToSave(user);
    }

    @GetMapping("/by")
    public ResponseEntity<User> getByEmail(String email) {
        return ResponseEntity.of(userRepository.getByEmail(email));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict(allEntries = true)
    public void enable(@PathVariable int id, @RequestParam boolean enabled) {
        log.info(enabled ? "enabled {}" : "disabled {}", id);
        User user = userRepository.getExisted(id);
        user.setEnabled(enabled);
    }

    @Override
    @GetMapping("/{id}/votes")
    public List<Vote> getAllVotes(@PathVariable int id) {
        return super.getAllVotes(id);
    }

    @Override
    @GetMapping("/{id}/votes/by")
    public Vote getVoteByDate(@PathVariable int id, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return super.getVoteByDate(id, date);
    }

    @Override
    @GetMapping("/{id}/votes/today")
    public Vote getVoteToday(@PathVariable int id){
        return super.getVoteToday(id);
    }
}
