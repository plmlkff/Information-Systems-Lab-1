package ru.itmo.is_lab1.exceptions.service;

public class CanNotAuthUserException extends Exception{
    public CanNotAuthUserException(String message) {
        super(message);
    }

    public CanNotAuthUserException() {
        super();
    }
}
