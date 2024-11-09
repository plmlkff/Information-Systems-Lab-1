package ru.itmo.is_lab1.exceptions.domain;

public class CanNotExecuteFunctionException extends Exception {
    public CanNotExecuteFunctionException() {
    }

    public CanNotExecuteFunctionException(String message) {
        super(message);
    }
}
