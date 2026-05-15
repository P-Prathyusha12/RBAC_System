package com.rbac.service;

import com.rbac.dto.request.RegisterRequest;
import com.rbac.dto.request.UserRequest;
import com.rbac.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponse registerUser(RegisterRequest request);
    UserResponse updateUser(Long id, UserRequest request);
    UserResponse getUserById(Long id);
    UserResponse getUserByUsername(String username);
    Page<UserResponse> getAllUsers(Pageable pageable);
    void deleteUser(Long id);
    void assignRoleToUser(Long userId, Long roleId);
    void removeRoleFromUser(Long userId, Long roleId);
    void enableUser(Long id);
    void disableUser(Long id);
}