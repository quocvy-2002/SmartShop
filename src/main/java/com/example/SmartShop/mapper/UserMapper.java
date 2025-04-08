package com.example.SmartShop.mapper;

import com.example.SmartShop.dto.request.CreateUserRequest;
import com.example.SmartShop.dto.request.UpdateUserRequest;
import com.example.SmartShop.dto.response.UserRespone;
import com.example.SmartShop.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")

public interface UserMapper {
    User toUser(CreateUserRequest request);
    UserRespone toUserRespone(User user);
    void updateUser(@MappingTarget User user, UpdateUserRequest request);
}
