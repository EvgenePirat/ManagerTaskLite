package com.example.ManagerTask.repository.impl;

import com.example.ManagerTask.domain.Task;
import com.example.ManagerTask.exception.ResourceMappingException;
import com.example.ManagerTask.repository.TaskRepository;
import com.example.ManagerTask.repository.DataSourceConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import com.example.ManagerTask.repository.mappers.TaskRowMapper;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {

    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID = """
            SELECT t.id,
                   t.tittle,
                   t.description,
                   t.expiration_date,
                   t.status
            FROM tasks t
            WHERE t.id = ?""";

    private final String FIND_ALL_BY_USER_ID = """
            SELECT t.id,
                   t.tittle,
                   t.description,
                   t.expiration_date,
                   t.status
            FROM tasks t
                     JOIN users_tasks ut on t.id = ut.task_id
            WHERE ut.user_id = ?""";

    private final String ASSIGN = """
            INSERT INTO users_tasks (task_id, user_id)
            VALUES (?, ?)""";

    private final String UPDATE = """
            UPDATE tasks
            SET tittle = ?,
                description = ?,
                expiration_date = ?,
                status = ?
            WHERE id = ?
            """;

    private final String CREATE = """
            INSERT INTO tasks (tittle, description, expiration_date, status)
            VALUES (?, ?, ?, ?)""";

    private final String DELETE = """
            DELETE FROM tasks
            WHERE id = ?""";

    @Override
    public Optional<Task> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1,id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                return Optional.ofNullable(TaskRowMapper.mapRow(resultSet));
            }
        }catch (SQLException e){
            throw new ResourceMappingException("Error while find task by id!");
        }
    }

    @Override
    public List<Task> findAllByUserId(Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_USER_ID);
            preparedStatement.setLong(1,userId);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                return TaskRowMapper.mapRows(resultSet);
            }
        }catch (SQLException e){
            throw new ResourceMappingException("Error while find all by task by id!");
        }
    }

    @Override
    public void assignToUserById(Long taskId, Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ASSIGN);
            preparedStatement.setLong(1,taskId);
            preparedStatement.setLong(2,userId);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new ResourceMappingException("Error while assigning to user!");
        }
    }

    @Override
    public void update(Task task) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1,task.getTittle());
            if(task.getDescription() == null){
                preparedStatement.setNull(2, Types.VARCHAR);
            }else {
                preparedStatement.setString(2,task.getDescription());
            }
            if(task.getDateTime() == null){
                preparedStatement.setNull(3, Types.TIMESTAMP);
            }else {
                preparedStatement.setTimestamp(3,Timestamp.valueOf(task.getDateTime()));
            }
            preparedStatement.setString(4,task.getStatus().name());
            preparedStatement.setLong(5,task.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new ResourceMappingException("Error while updating task!");
        }
    }

    @Override
    public void create(Task task) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,task.getTittle());
            if(task.getDescription() == null){
                preparedStatement.setNull(2, Types.VARCHAR);
            }else {
                preparedStatement.setString(2,task.getDescription());
            }
            if(task.getDateTime() == null){
                preparedStatement.setNull(3, Types.TIMESTAMP);
            }else {
                preparedStatement.setTimestamp(3,Timestamp.valueOf(task.getDateTime()));
            }
            preparedStatement.setString(4,task.getStatus().name());
            preparedStatement.executeUpdate();
            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                resultSet.next();
                task.setId(resultSet.getLong(1));
            }
        }catch (SQLException e){
            throw new ResourceMappingException("Error while creating task!");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new ResourceMappingException("Error while to delete task!");
        }
    }
}
