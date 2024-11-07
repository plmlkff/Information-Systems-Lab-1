package ru.itmo.is_lab1.exceptions.domain;

public class CanNotUpdateEntityException extends Exception{
    public CanNotUpdateEntityException(String msg){
        super(msg);
    }

    public CanNotUpdateEntityException() {
        super();
    }
}
