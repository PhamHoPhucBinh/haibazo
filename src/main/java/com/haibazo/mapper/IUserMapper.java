package com.haibazo.mapper;

import com.haibazo.dto.request.UserCreateRequest;
import com.haibazo.dto.request.UserUpdateRequest;
import com.haibazo.dto.response.UserResponse;
import com.haibazo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "userId", ignore = true)
    User toUser(UserCreateRequest userCreateRequest);

    UserResponse toUserResponse(User user);

    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "username", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);
}
