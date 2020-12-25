package com.restaurant.restaurantvotingrestapi.exception;

public class NotFoundException extends IllegalRequestDataException {
    public NotFoundException(String msg) {
        super(msg);
    }
}