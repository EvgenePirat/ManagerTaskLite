package com.example.ManagerTask.web.mappers;

import com.example.ManagerTask.domain.User;
import com.example.ManagerTask.web.dto.model.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}
