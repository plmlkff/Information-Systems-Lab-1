package ru.itmo.is_lab1.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "file_upload_history")
@Data
public class FileUploadHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "login")
    @NotNull
    private User user;

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    private State state;

    @Column
    @NotNull
    private Date time;

    @Column
    @NotNull
    private Integer count;

    @Column(name = "file_name", unique = true)
    private String fileName;

    public interface Fields {
        String USER = "user";
        String FILE_NAME = "fileName";
    }

    public interface Columns{

    }


    public enum State{
        UPLOADED,
        CANCELED
    }
}
