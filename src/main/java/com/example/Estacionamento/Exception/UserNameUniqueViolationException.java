package com.example.Estacionamento.Exception;

public class UserNameUniqueViolationException extends RuntimeException {
    public UserNameUniqueViolationException(String s) {
        super(s);
    }
}
