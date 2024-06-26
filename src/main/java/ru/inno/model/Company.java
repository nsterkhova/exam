package ru.inno.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class Company {
    private Integer id;
    private Boolean isActive;
    private String createDateTime;
    private String lastChangedDateTime;
    private String name;
    private String description;
    private Timestamp deletedAt;

    public Company(Boolean isActive, String name, String description){
        this.isActive = isActive;
        this.name = name;
        this.description = description;
    }

    public Company(Integer id, Boolean isActive, String name, String description){
        this.id = id;
        this.isActive = isActive;
        this.name = name;
        this.description = description;
    }

}
