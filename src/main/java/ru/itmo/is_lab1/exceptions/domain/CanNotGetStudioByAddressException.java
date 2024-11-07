package ru.itmo.is_lab1.exceptions.domain;

public class CanNotGetStudioByAddressException extends Exception{
    public CanNotGetStudioByAddressException() {
        super();
    }

    public CanNotGetStudioByAddressException(String message) {
        super(message);
    }
}
