package com.example.ManagerTask.service;

import com.example.ManagerTask.domain.Task;

import java.util.List;

public interface TaskService {
    Task getById(Long id);

    List<Task> getAllByUser(Long userId);

    Task update(Task task);

    Task create(Task task, Long id);

    void delete(Long taskId);

}
