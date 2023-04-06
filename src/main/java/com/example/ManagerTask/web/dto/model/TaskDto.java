package com.example.ManagerTask.web.dto.model;

import com.example.ManagerTask.domain.Status;
import com.example.ManagerTask.web.dto.validation.OnCreate;
import com.example.ManagerTask.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class TaskDto {
    @NotNull(message = "Id must be not null", groups = OnUpdate.class)
    private Long id;

    @NotNull(message = "tittle must be not null", groups = {OnUpdate.class, OnCreate.class})
    @Length(max = 255, message = "Tittle must be smaller then 255 chars", groups = {OnUpdate.class, OnCreate.class})
    private String tittle;

    @Length(max = 255, message = "Tittle must be smaller then 255 chars", groups = {OnUpdate.class, OnCreate.class})
    private String description;

    private Status status;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm")
    private LocalDateTime dateTime;
}
