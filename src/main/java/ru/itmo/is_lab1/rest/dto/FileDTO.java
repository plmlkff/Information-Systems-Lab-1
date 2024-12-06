package ru.itmo.is_lab1.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class FileDTO {
    byte[] bytes;
}
