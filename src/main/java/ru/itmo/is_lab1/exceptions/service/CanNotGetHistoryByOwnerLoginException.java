package ru.itmo.is_lab1.exceptions.service;

public class CanNotGetHistoryByOwnerLoginException extends Exception{
    public CanNotGetHistoryByOwnerLoginException(String message) {
        super(message);
    }
}
