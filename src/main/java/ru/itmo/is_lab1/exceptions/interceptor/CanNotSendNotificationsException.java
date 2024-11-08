package ru.itmo.is_lab1.exceptions.interceptor;

public class CanNotSendNotificationsException extends Exception{
    public CanNotSendNotificationsException() {
    }

    public CanNotSendNotificationsException(String message) {
        super(message);
    }
}
