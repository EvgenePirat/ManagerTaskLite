package com.example.ManagerTask.web.controller;

import com.example.ManagerTask.domain.Task;
import com.example.ManagerTask.domain.User;
import com.example.ManagerTask.service.TaskService;
import com.example.ManagerTask.service.UserService;
import com.example.ManagerTask.web.dto.model.TaskDto;
import com.example.ManagerTask.web.dto.model.UserDto;
import com.example.ManagerTask.web.dto.validation.OnCreate;
import com.example.ManagerTask.web.dto.validation.OnUpdate;
import com.example.ManagerTask.web.mappers.TaskMapper;
import com.example.ManagerTask.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;
    private final TaskService taskService;
    private final UserMapper userMapper;
    private final TaskMapper taskMapper;

    @PutMapping
    public UserDto updateUser(@Validated(OnUpdate.class) @RequestBody UserDto userDto){
        User user = userMapper.toEntity(userDto);
        User userUpdate = userService.update(user);
        return userMapper.toDto(userUpdate);
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable long id){
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.delete(id);
    }

    @GetMapping("/{id}/tasks")
    public List<TaskDto> getTaskByUserId(@PathVariable Long id){
        List<Task> taskList = taskService.getAllByUser(id);
        return taskMapper.toDto(taskList);
    }

    @PostMapping("/{id}/tasks")
    public TaskDto createTask(@PathVariable Long id, @Validated(OnCreate.class) @RequestBody TaskDto taskDto){
        Task task = taskMapper.toEntity(taskDto);
        Task taskCreate = taskService.create(task,id);
        return taskMapper.toDto(taskCreate);
    }
}
