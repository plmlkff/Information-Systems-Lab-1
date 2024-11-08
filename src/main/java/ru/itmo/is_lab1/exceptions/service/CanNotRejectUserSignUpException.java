package ru.itmo.is_lab1.exceptions.service;

public class CanNotRejectUserSignUpException extends Exception{
    public CanNotRejectUserSignUpException() {
    }

    public CanNotRejectUserSignUpException(String message) {
        super(message);
    }
}
