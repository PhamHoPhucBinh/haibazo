package com.haibazo.controller;

import com.haibazo.dto.request.UserCreateRequest;
import com.haibazo.dto.request.UserUpdateRequest;
import com.haibazo.dto.response.ApiResponse;
import com.haibazo.dto.response.UserResponse;
import com.haibazo.mapper.IUserMapper;
import com.haibazo.model.User;
import com.haibazo.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;
    IUserMapper userMapper;

    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers() {
        List<UserResponse> userResponses = userService.getAllUsers();
        return ApiResponse.<List<UserResponse>>builder().code(200).result(userResponses).build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable(value = "id") Integer userId) {
        User user = userService.getUserById(userId);
        return ApiResponse.<UserResponse>builder().code(200).result(userMapper.toUserResponse(user)).build();
    }

    @GetMapping("/email/{email}")
    public ApiResponse<UserResponse> getUserByEmail(@PathVariable(value = "email") String email) {
        User user = userService.getUserByEmail(email);
        return ApiResponse.<UserResponse>builder().code(200).result(userMapper.toUserResponse(user)).build();
    }

    @GetMapping("/username/{username}")
    public ApiResponse<UserResponse> getUserByUsername(@PathVariable(value = "username") String username) {
        User user = userService.getUserByUsername(username);
        return ApiResponse.<UserResponse>builder().code(200).result(userMapper.toUserResponse(user)).build();
    }

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody UserCreateRequest userCreateRequest) {
        UserResponse userResponse = userService.saveUser(userCreateRequest);
        return ApiResponse.<UserResponse>builder().code(200).result(userResponse).build();
    }

    @PutMapping("update/{id}")
    public ApiResponse<UserResponse> updateUser(@PathVariable(value = "id") Integer userId,
                                                @RequestBody UserUpdateRequest userDetails) {
        UserResponse userResponse = userService.updateUser(userId, userDetails);
        return ApiResponse.<UserResponse>builder().code(200).result(userResponse).build();
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse<String> deleteUser(@PathVariable(value = "id") Integer userId) {
        userService.deleteUser(userId);
        return ApiResponse.<String>builder().code(200).result("Delete User Successful").build();
    }
}
