package com.example.ManagerTask.web.mappers;

import com.example.ManagerTask.domain.Task;
import com.example.ManagerTask.web.dto.model.TaskDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto toDto(Task task);
    List<TaskDto>toDto(List<Task> list);
    Task toEntity(TaskDto dto);
}
