package com.example.ManagerTask.web.controller;

import com.example.ManagerTask.domain.Task;
import com.example.ManagerTask.service.TaskService;
import com.example.ManagerTask.web.dto.model.TaskDto;
import com.example.ManagerTask.web.dto.validation.OnUpdate;
import com.example.ManagerTask.web.mappers.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Validated
public class TaskController {

    private TaskService taskService;

    private TaskMapper taskMapper;

    @GetMapping("/{id}")
    public TaskDto getById(@PathVariable Long taskId){
        Task task = taskService.getById(taskId);
        return taskMapper.toDto(task);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        taskService.delete(id);
    }

    @PutMapping
    public TaskDto update(@Validated(OnUpdate.class) @RequestBody TaskDto taskDto){
        Task task = taskMapper.toEntity(taskDto);
        Task taskReturned = taskService.update(task);
        return taskMapper.toDto(taskReturned);
    }

}
