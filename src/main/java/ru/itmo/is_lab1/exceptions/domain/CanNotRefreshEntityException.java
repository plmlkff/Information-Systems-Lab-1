package ru.itmo.is_lab1.exceptions.domain;

public class CanNotRefreshEntityException extends Exception{
    public CanNotRefreshEntityException(String msg){
        super(msg);
    }

    public CanNotRefreshEntityException() {
        super();
    }
}
