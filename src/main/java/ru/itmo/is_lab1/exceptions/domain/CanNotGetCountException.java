package ru.itmo.is_lab1.exceptions.domain;

public class CanNotGetCountException extends Exception{
    public CanNotGetCountException() {
    }

    public CanNotGetCountException(String message) {
        super(message);
    }
}
