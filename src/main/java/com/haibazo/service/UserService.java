package com.haibazo.service;

import com.haibazo.dto.request.UserCreateRequest;
import com.haibazo.dto.request.UserUpdateRequest;
import com.haibazo.dto.response.UserResponse;
import com.haibazo.exception.AppException;
import com.haibazo.exception.ErrorCode;
import com.haibazo.mapper.IUserMapper;
import com.haibazo.model.User;
import com.haibazo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    IUserMapper iUserMapper;
    UserRepository userRepository;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(iUserMapper::toUserResponse).toList();
    }

    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    public UserResponse updateUser(Integer userId, UserUpdateRequest request) {
        User user = this.getUserById(userId);
        iUserMapper.updateUser(user, request);
        user = userRepository.save(user);
        return iUserMapper.toUserResponse(user);
    }


    public UserResponse saveUser(UserCreateRequest userCreateRequest) {
        User user = iUserMapper.toUser(userCreateRequest);
        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(
                    ErrorCode.USER_EXIST);
        }
        return iUserMapper.toUserResponse(user);
    }

    public void deleteUser(Integer userId) {
        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

    }
}
