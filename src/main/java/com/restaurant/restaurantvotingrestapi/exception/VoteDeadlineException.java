package com.restaurant.restaurantvotingrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Ivan Kurilov on 30.12.2020
 */
@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class VoteDeadlineException extends RuntimeException {
    public VoteDeadlineException(String msg) {
        super(msg);
    }
}
