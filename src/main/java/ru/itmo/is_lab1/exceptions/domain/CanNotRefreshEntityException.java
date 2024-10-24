package ru.itmo.is_lab1.exceptions.domain;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CanNotRefreshEntityException extends Exception{
    public CanNotRefreshEntityException(String msg){
        super(msg);
    }
}
