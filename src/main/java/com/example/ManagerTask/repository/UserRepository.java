package com.example.ManagerTask.repository;

import com.example.ManagerTask.domain.Role;
import com.example.ManagerTask.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    void updateUser(User user);

    void create(User user);

    void insertUserRole(Long userId, Role role);

    boolean isTaskOwner(Long userId, Long taskId);

    void delete(Long userId);
}
