package com.example.ManagerTask.service.impl;

import com.example.ManagerTask.domain.Role;
import com.example.ManagerTask.domain.User;
import com.example.ManagerTask.exception.ResourcesNotFoundException;
import com.example.ManagerTask.repository.UserRepository;
import com.example.ManagerTask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new ResourcesNotFoundException("User not found!"));
    }

    @Override
    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()->new ResourcesNotFoundException("User not found!"));
    }

    @Override
    @Transactional
    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.updateUser(user);
        return user;
    }

    @Override
    @Transactional
    public User create(User user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new IllegalStateException("User already create");
        }
        if(!user.getPassword().equals(user.getPasswordConfiguration())){
            throw new IllegalStateException("Password and password configuration do not match");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.create(user);
        Set<Role> roleSet = Set.of(Role.ROLE_USER);
        userRepository.insertUserRole(user.getId(), Role.ROLE_USER);
        user.setRoles(roleSet);
        return user;
    }

    @Override
    @Transactional
    public boolean isTaskOwner(Long userId, Long taskId) {
        return userRepository.isTaskOwner(userId,taskId);
    }

    @Override
    @Transactional
    public void delete(Long userId) {
        userRepository.delete(userId);
    }
}
