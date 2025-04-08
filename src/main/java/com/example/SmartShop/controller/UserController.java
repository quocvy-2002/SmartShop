package com.example.SmartShop.controller;

import com.example.SmartShop.dto.request.CreateUserRequest;
import com.example.SmartShop.dto.request.UpdateUserRequest;
import com.example.SmartShop.dto.response.ApiResponse;
import com.example.SmartShop.dto.response.UserRespone;
import com.example.SmartShop.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;
    @PostMapping
    ApiResponse<UserRespone> createUser(@Valid @RequestBody CreateUserRequest request) {
        System.out.println("Request: " + request);
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }

    // chỉ ad min mới get được
    @GetMapping
    ApiResponse<List<UserRespone>> getUsers() {
        return ApiResponse.<List<UserRespone>>builder()
                .result(userService.getUsers())
                .build();
    }
    // chỉ admin mới get được
    @GetMapping("/{userId}")
    ApiResponse<UserRespone> getUser(@PathVariable("userId") Integer userId) {
        return ApiResponse.<UserRespone>builder()
                .result(userService.getUser(userId))
                .build();
    }

    @GetMapping("/my-info")
    ApiResponse<UserRespone> getMyInfo() {
        return ApiResponse.<UserRespone>builder()
                .result(userService.getMyInfo())
                .build();
    }

    // chỉ admin mới xóa được
    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserRespone> updateUser(@PathVariable Integer userId,@Valid @RequestBody UpdateUserRequest request) {
        System.out.println("Request: " + request);
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(userService.updateUser(userId,request));
        return apiResponse;
    }

}
