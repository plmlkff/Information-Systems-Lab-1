package ru.itmo.is_lab1.exceptions.service;

public class CanNotApproveUserSignUpException extends Exception{
    public CanNotApproveUserSignUpException() {
    }

    public CanNotApproveUserSignUpException(String message) {
        super(message);
    }
}
