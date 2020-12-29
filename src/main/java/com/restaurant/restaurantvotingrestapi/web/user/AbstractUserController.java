package com.restaurant.restaurantvotingrestapi.web.user;

import com.restaurant.restaurantvotingrestapi.model.HasId;
import com.restaurant.restaurantvotingrestapi.model.User;
import com.restaurant.restaurantvotingrestapi.model.Vote;
import com.restaurant.restaurantvotingrestapi.repository.UserRepository;
import com.restaurant.restaurantvotingrestapi.repository.VoteRepository;
import com.restaurant.restaurantvotingrestapi.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.time.LocalDate;
import java.util.List;

import static com.restaurant.restaurantvotingrestapi.util.ValidationUtil.assureIdConsistent;
import static com.restaurant.restaurantvotingrestapi.util.ValidationUtil.checkSingleModification;

/**
 * @author Ivan Kurilov on 28.12.2020
 */

@Slf4j
public class AbstractUserController {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected VoteRepository voteRepository;

    @Autowired
    private UniqueMailValidator emailValidator;

    @Autowired
    private LocalValidatorFactoryBean defaultValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }

    public ResponseEntity<User> get(int id){
        log.info("get {}", id);
        return ResponseEntity.of(userRepository.findById(id));
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id){
        log.info("deleted {}", id);
        checkSingleModification(userRepository.delete(id), "User id=" + id + "not found");
    }

    public ResponseEntity<User> getWithVotes(int id){
        log.info("getWithVotes {}", id);
        return ResponseEntity.of(userRepository.getWithVotes(id));
    }

    protected User prepareToSave(User user){
        return userRepository.save(UserUtil.prepareToSave(user));
    }

    protected void validateBeforeUpdate(HasId user, int id) throws BindException {
        assureIdConsistent(user, id);
        DataBinder binder = new DataBinder(user);
        binder.addValidators(emailValidator, defaultValidator);
        binder.validate();
        if(binder.getBindingResult().hasErrors()){
            throw new BindException(binder.getBindingResult());
        }
    }

    public List<Vote> getAllVotes(int id){
        log.info("getAllVotes {}", id);
        return voteRepository.getAll(id);
    }

    public Vote getVoteByDate(int id, LocalDate dateVote){
        log.info("getVoteByDate for user={} by date={}", id, dateVote);
        return voteRepository.getByUserIdAndDateVote(id, dateVote);
    }

    public Vote getVoteToday(int id){
        log.info("getVoteToday for user={}", id);
        return voteRepository.getByUserIdAndDateVote(id, LocalDate.now());
    }
}
