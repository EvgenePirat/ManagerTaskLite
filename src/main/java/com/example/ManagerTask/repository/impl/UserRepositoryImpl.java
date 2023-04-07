package com.example.ManagerTask.repository.impl;

import com.example.ManagerTask.domain.Role;
import com.example.ManagerTask.domain.User;
import com.example.ManagerTask.exception.ResourceMappingException;
import com.example.ManagerTask.repository.UserRepository;
import com.example.ManagerTask.repository.DataSourceConfig;
import com.example.ManagerTask.repository.mappers.UserRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final DataSourceConfig dataSourceConfig;
    private final String FIND_BY_ID = """
            SELECT u.id as user_id,
                   u.nameUser as user_name,
                   u.username as user_username,
                   u.password as user_password,
                   ur.role as user_role_role,
                   t.id as task_id,
                   t.tittle as task_title,
                   t.description as task_description,
                   t.expiration_date as task_expiration_date,
                   t.status as task_status
            FROM users u
                LEFT JOIN users_roles ur on u.id = ur.user_id
                LEFT JOIN users_tasks ut on u.id = ut.user_id
                LEFT JOIN tasks t on ut.task_id = t.id
            WHERE u.id = ?""";

    private final String FIND_BY_USERNAME = """
            SELECT u.id as user_id,
                   u.nameUser as user_name,
                   u.username as user_username,
                   u.password as user_password,
                   ur.role as user_role_role,
                   t.id as task_id,
                   t.tittle as task_title,
                   t.description as task_description,
                   t.expiration_date as task_expiration_date,
                   t.status as task_status
            FROM users u
                LEFT JOIN users_roles ur on u.id = ur.user_id
                LEFT JOIN users_tasks ut on u.id = ut.user_id
                LEFT JOIN tasks t on ut.task_id = t.id
            WHERE u.username = ?""";

    private final String UPDATE = """
            UPDATE users
            SET nameUser = ?,
                username = ?,
                password = ?
            WHERE id = ?""";

    private final String CREATE = """
            INSERT INTO users (nameUser, username, password)
            VALUES (?, ?, ?)""";

    private final String INSERT_USER_ROLE = """
            INSERT INTO users_roles (user_id, role)
            VALUES (?, ?)""";

    private final String IS_TASK_OWNER = """
            SELECT exists(
                           SELECT 1
                           FROM users_tasks
                           WHERE user_id = ?
                             AND task_id = ?
                       )""";

    private final String DELETE = """
            DELETE FROM users
            WHERE id = ?""";

    @Override
    public Optional<User> findById(Long id) {
         try {
             Connection connection = dataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
             preparedStatement.setLong(1,id);
             try(ResultSet resultSet = preparedStatement.executeQuery()){
                return Optional.ofNullable(UserRowMapper.mapRow(resultSet));
             }

         }catch (SQLException throwables){
             throw new ResourceMappingException("Exception while finding user by id!");
         }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_USERNAME, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1,username);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                return Optional.ofNullable(UserRowMapper.mapRow(resultSet));
            }

        }catch (SQLException throwables){
            throw new ResourceMappingException("Exception while finding user by username!");
        }
    }

    @Override
    public void updateUser(User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setLong(4,user.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException throwables){
            throw new ResourceMappingException("Exception while updating user!");
        }
    }

    @Override
    public void create(User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();
            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                resultSet.next();
                user.setId(resultSet.getLong(1));
            }
        }catch (SQLException throwables){
            throw new ResourceMappingException("Exception while creating user!");
        }
    }

    @Override
    public void insertUserRole(Long userId, Role role) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_ROLE);
            preparedStatement.setLong(1,userId);
            preparedStatement.setString(2,role.name());
            preparedStatement.executeUpdate();
        }catch (SQLException throwables){
            throw new ResourceMappingException("Exception while inserting role to user!");
        }
    }

    @Override
    public boolean isTaskOwner(Long userId, Long taskId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(IS_TASK_OWNER);
            preparedStatement.setLong(1,userId);
            preparedStatement.setLong(2,taskId);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }
        }catch (SQLException throwables){
            throw new ResourceMappingException("Exception while checking if user is task owner!");
        }
    }

    @Override
    public void delete(Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1,userId);
            preparedStatement.executeUpdate();
        }catch (SQLException throwables){
            throw new ResourceMappingException("Exception while deleting user!");
        }
    }
}
