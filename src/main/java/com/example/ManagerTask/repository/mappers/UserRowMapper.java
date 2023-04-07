package com.example.ManagerTask.repository.mappers;

import com.example.ManagerTask.domain.Role;
import com.example.ManagerTask.domain.Task;
import com.example.ManagerTask.domain.User;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRowMapper {

    @SneakyThrows
    public static User mapRow(ResultSet resultSet){
        Set<Role> roleSet = new HashSet<>();
        while (resultSet.next()){
            roleSet.add(Role.valueOf(resultSet.getString("user_role_role")));
        }
        resultSet.beforeFirst();
        List<Task> taskList = TaskRowMapper.mapRows(resultSet);
        resultSet.beforeFirst();
        User user = null;
        if(resultSet.next()){
            user = new User();
            user.setRoles(roleSet);
            user.setTasks(taskList);
            user.setId(resultSet.getLong("user_id"));
            user.setName(resultSet.getString("user_name"));
            user.setUsername(resultSet.getString("user_username"));
            user.setPassword(resultSet.getString("user_password"));
        }
        return user;
    }
}
