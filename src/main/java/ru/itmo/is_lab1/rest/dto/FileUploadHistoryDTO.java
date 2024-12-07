package ru.itmo.is_lab1.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.itmo.is_lab1.domain.entity.FileUploadHistory;
import ru.itmo.is_lab1.domain.entity.FileUploadHistory.State;

import java.util.Date;

@Data
public class FileUploadHistoryDTO {
    @NotNull
    private String login;

    @NotNull
    private State state;

    @NotNull
    private Date time;

    @NotNull
    private Integer count;

    public static FileUploadHistoryDTO fromDomain(FileUploadHistory fileUploadHistory){
        var fileUploadHistoryDTO = new FileUploadHistoryDTO();
        fileUploadHistoryDTO.setLogin(fileUploadHistory.getUser().getLogin());
        fileUploadHistoryDTO.setState(fileUploadHistory.getState());
        fileUploadHistoryDTO.setCount(fileUploadHistory.getCount());
        fileUploadHistoryDTO.setTime(fileUploadHistory.getTime());
        return fileUploadHistoryDTO;
    }
}
