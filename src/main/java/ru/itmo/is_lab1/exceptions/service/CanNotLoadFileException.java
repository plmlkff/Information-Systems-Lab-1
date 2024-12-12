package ru.itmo.is_lab1.exceptions.service;

public class CanNotLoadFileException extends Exception{
    public CanNotLoadFileException(String message) {
        super(message);
    }
}
