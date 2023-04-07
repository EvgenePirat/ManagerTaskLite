package com.example.ManagerTask.repository.mappers;

import com.example.ManagerTask.domain.Status;
import com.example.ManagerTask.domain.Task;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TaskRowMapper {
    @SneakyThrows
    public static Task mapRow(ResultSet resultSet){
        if(resultSet.next()){
            Task task = new Task();
            task.setId(resultSet.getLong("id"));
            task.setTittle(resultSet.getString("tittle"));
            task.setDescription(resultSet.getString("description"));
            task.setStatus(Status.valueOf(resultSet.getString("status")));
            Timestamp timestamp = resultSet.getTimestamp("expiration_date");
            if(timestamp != null){
                task.setDateTime(timestamp.toLocalDateTime());
            }
            return task;
        }
        return null;
    }

    @SneakyThrows
    public static List<Task> mapRows(ResultSet resultSet){
        List<Task> taskList = new ArrayList<>();
        if(resultSet.next()){
            Task task = new Task();
            task.setId(resultSet.getLong("id"));
            if(!resultSet.wasNull()){
                task.setTittle(resultSet.getString("tittle"));
                task.setDescription(resultSet.getString("description"));
                task.setStatus(Status.valueOf(resultSet.getString("status")));
                Timestamp timestamp = resultSet.getTimestamp("expiration_date");
                if(timestamp != null){
                    task.setDateTime(timestamp.toLocalDateTime());
                }
                taskList.add(task);
            }
        }
        return taskList;
    }
}
