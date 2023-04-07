package com.example.ManagerTask.service.impl;

import com.example.ManagerTask.domain.Status;
import com.example.ManagerTask.domain.Task;
import com.example.ManagerTask.exception.ResourceMappingException;
import com.example.ManagerTask.exception.ResourcesNotFoundException;
import com.example.ManagerTask.repository.TaskRepository;
import com.example.ManagerTask.repository.UserRepository;
import com.example.ManagerTask.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    @Transactional(readOnly = true)
    public Task getById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(()->new ResourcesNotFoundException("Task not found!"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getAllByUser(Long userId) {
        return taskRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public Task update(Task task) {
        if(task.getStatus() == null){
            task.setStatus(Status.ACTIVE);
        }
        taskRepository.update(task);
        return task;
    }

    @Override
    @Transactional
    public Task create(Task task, Long userId) {
        task.setStatus(Status.ACTIVE);
        taskRepository.create(task);
        taskRepository.assignToUserById(task.getId(), userId);
        return task;
    }

    @Override
    @Transactional
    public void delete(Long taskId) {
        taskRepository.delete(taskId);
    }
}
