package ru.itmo.is_lab1.exceptions.service;

public class CanNotSaveFromFileException extends Exception{
    public CanNotSaveFromFileException(String message) {
        super(message);
    }
}
