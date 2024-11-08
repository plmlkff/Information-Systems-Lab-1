package ru.itmo.is_lab1.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "studio")
@Data
public class Studio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotNull
    private String address; //Поле не может быть null

    public void merge(Studio newStudio){
        address = newStudio.getAddress();
    }

    public interface Columns{
        String ADDRESS = "address";
    }
}
