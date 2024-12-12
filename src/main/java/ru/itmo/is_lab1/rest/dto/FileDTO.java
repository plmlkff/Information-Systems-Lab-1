package ru.itmo.is_lab1.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO {
    String name;

    byte[] bytes;
}
