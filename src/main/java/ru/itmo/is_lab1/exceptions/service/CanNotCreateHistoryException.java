package ru.itmo.is_lab1.exceptions.service;

public class CanNotCreateHistoryException extends Exception{
    public CanNotCreateHistoryException() {
    }

    public CanNotCreateHistoryException(String message) {
        super(message);
    }
}
