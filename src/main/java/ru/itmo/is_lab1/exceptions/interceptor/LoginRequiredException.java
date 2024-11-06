package ru.itmo.is_lab1.exceptions.interceptor;

public class LoginRequiredException extends Exception{
    public LoginRequiredException(String msg){
        super(msg);
    }

    public LoginRequiredException() {
        super();
    }
}
