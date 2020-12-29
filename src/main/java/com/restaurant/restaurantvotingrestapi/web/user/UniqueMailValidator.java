package com.restaurant.restaurantvotingrestapi.web.user;

import com.restaurant.restaurantvotingrestapi.model.HasIdAndEmail;
import com.restaurant.restaurantvotingrestapi.repository.UserRepository;
import com.restaurant.restaurantvotingrestapi.web.GlobalExceptionHandler;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Ivan Kurilov on 28.12.2020
 */
@Component
@NoArgsConstructor
public class UniqueMailValidator implements Validator {

    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return HasIdAndEmail.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        HasIdAndEmail user = (HasIdAndEmail) o;
        if (StringUtils.hasText(user.getEmail())) {
            if (userRepository.getByEmail(user.getEmail().toLowerCase())
                    .filter(u -> u.getId().equals(user.getId())).isPresent()){
                errors.rejectValue("email", "", GlobalExceptionHandler.EXCEPTION_DUPLICATE_EMAIL);
            }

        }
    }
}
