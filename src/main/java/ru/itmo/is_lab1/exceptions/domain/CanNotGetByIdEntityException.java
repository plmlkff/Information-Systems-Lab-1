package ru.itmo.is_lab1.exceptions.domain;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CanNotGetByIdEntityException extends Exception{
    public CanNotGetByIdEntityException(String msg){
        super(msg);
    }
}
