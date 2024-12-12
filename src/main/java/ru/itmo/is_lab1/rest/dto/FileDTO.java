package ru.itmo.is_lab1.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class FileDTO {
    String name;

    byte[] bytes;
}
