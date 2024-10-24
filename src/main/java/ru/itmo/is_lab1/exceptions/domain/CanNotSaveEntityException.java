package ru.itmo.is_lab1.exceptions.domain;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CanNotSaveEntityException extends Exception{
    public CanNotSaveEntityException(String msg){
        super(msg);
    }
}
