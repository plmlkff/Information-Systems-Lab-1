package ru.itmo.is_lab1.exceptions.service;

public class CanNotSignUpUserException extends Exception{
    public CanNotSignUpUserException(String message) {
        super(message);
    }

    public CanNotSignUpUserException() {
        super();
    }
}
