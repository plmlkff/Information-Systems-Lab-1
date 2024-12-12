package ru.itmo.is_lab1.exceptions.service;

public class CanNotSaveFileException extends Exception{
    public CanNotSaveFileException(String message) {
        super(message);
    }
}
