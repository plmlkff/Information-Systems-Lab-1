package ru.itmo.is_lab1.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.itmo.is_lab1.domain.entity.EntityChangeHistory;

import java.util.Date;

@Data
public class EntityChangeHistoryDTO {
    @NotNull
    private String login;

    @NotNull
    private EntityChangeHistory.OperationType operation;

    @NotNull
    private Integer musicBandId;

    @NotNull
    private Date time;

    public static EntityChangeHistoryDTO fromDomain(EntityChangeHistory entityChangeHistory){
        EntityChangeHistoryDTO entityChangeHistoryDTO = new EntityChangeHistoryDTO();
        entityChangeHistoryDTO.setLogin(entityChangeHistory.getUser().getLogin());
        entityChangeHistoryDTO.setOperation(entityChangeHistory.getOperation());
        entityChangeHistoryDTO.setMusicBandId(entityChangeHistory.getMusicBandId());
        entityChangeHistoryDTO.setTime(entityChangeHistory.getTime());
        return entityChangeHistoryDTO;
    }
}
