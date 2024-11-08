package ru.itmo.is_lab1.exceptions.domain;

public class CanNotGetHistoryByEntityIdException extends Exception{
    public CanNotGetHistoryByEntityIdException() {
    }

    public CanNotGetHistoryByEntityIdException(String message) {
        super(message);
    }
}
