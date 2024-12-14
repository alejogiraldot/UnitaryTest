package com.demo.domain.exceptions;

public class NotEnoughAmountException extends RuntimeException{
    public NotEnoughAmountException(String message){
        super(message);
    }
}
