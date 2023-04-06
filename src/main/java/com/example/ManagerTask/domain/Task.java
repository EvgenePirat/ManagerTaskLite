package com.example.ManagerTask.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Task {
    private Long id;
    private String tittle;
    private String description;
    private Status status;
    private LocalDateTime dateTime;

}
