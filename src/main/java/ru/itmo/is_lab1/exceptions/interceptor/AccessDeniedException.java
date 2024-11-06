package ru.itmo.is_lab1.exceptions.interceptor;

public class AccessDeniedException extends Exception{
    public AccessDeniedException(String msg) {
        super(msg);
    }

    public AccessDeniedException(){
        super();
    }
}
