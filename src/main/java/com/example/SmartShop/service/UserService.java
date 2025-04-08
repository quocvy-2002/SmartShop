package com.example.SmartShop.service;

import com.example.SmartShop.constant.PredefinedRole;
import com.example.SmartShop.dto.request.CreateUserRequest;
import com.example.SmartShop.dto.request.UpdateUserRequest;
import com.example.SmartShop.dto.response.UserRespone;
import com.example.SmartShop.entity.Role;
import com.example.SmartShop.entity.User;
import com.example.SmartShop.exception.AppException;
import com.example.SmartShop.exception.ErrorCode;
import com.example.SmartShop.mapper.UserMapper;
import com.example.SmartShop.repository.RoleRepository;
import com.example.SmartShop.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder ;
    RoleRepository roleRepository;

    public UserRespone createUser(CreateUserRequest request){
        if (userRepository.existsByUserName
                (request.getUserName())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.INVALID_EMAIL);
        }
        if (userRepository.existsByNumberPhone(request.getNumberPhone())) {
            throw new AppException(ErrorCode.INVALID_PHONE_NUMBER);
        }
        LocalDateTime now = LocalDateTime.now();
        User user = userMapper.toUser(request);
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Role roleUser = roleRepository.findByName(PredefinedRole.USER_ROLE)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        user.setRole(roleUser);
        return userMapper.toUserRespone(userRepository.save(user));
    }
    @Transactional
    public UserRespone updateUser( Integer userId ,UpdateUserRequest request){
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.updateUser(user,request);
        user.setEmail(request.getEmail());
        user.setUserName(request.getUserName());
        user.setNumberPhone(request.getNumberPhone());
        user.setGender(request.getGender());
        LocalDateTime now = LocalDateTime.now();
        user.setUpdatedAt(now);
        return userMapper.toUserRespone(userRepository.save(user));
    }

    public UserRespone getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String userName = context.getAuthentication().getName();
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserRespone(user);
    }

    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    public List<UserRespone> getUsers() {
        log.info("In method get Users");
        return userRepository.findAll().stream().map(userMapper::toUserRespone).toList();
    }

    public UserRespone getUser(Integer userId) {
        return userMapper.toUserRespone(
                userRepository.findByUserId(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }


}
