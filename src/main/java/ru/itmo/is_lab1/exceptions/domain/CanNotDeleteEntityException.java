package ru.itmo.is_lab1.exceptions.domain;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CanNotDeleteEntityException extends Exception{
    public CanNotDeleteEntityException(String msg){
        super(msg);
    }
}
