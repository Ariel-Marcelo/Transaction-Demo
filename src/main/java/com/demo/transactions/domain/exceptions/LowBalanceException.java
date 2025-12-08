package com.demo.transactions.domain.exceptions;

public class LowBalanceException extends RuntimeException{
    public LowBalanceException(String message) {
        super(message);
    }
}
