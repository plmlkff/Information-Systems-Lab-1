package ru.itmo.is_lab1.exceptions.util;

public class CanNotCreateHashException extends Exception {
    public CanNotCreateHashException(String msg) {
        super(msg);
    }

    public CanNotCreateHashException() {
        super();
    }
}