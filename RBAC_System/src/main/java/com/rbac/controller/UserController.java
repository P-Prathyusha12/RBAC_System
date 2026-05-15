package com.rbac.controller;

import com.rbac.dto.request.UserRequest;
import com.rbac.dto.response.ApiResponse;
import com.rbac.dto.response.UserResponse;
import com.rbac.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    @PreAuthorize("hasAuthority('USER_READ') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<UserResponse>>> getAllUsers(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<UserResponse> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(ApiResponse.success(users, "Users retrieved successfully"));
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_READ') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(user, "User retrieved successfully"));
    }
    
    @GetMapping("/username/{username}")
    @PreAuthorize("hasAuthority('USER_READ') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByUsername(@PathVariable String username) {
        UserResponse user = userService.getUserByUsername(username);
        return ResponseEntity.ok(ApiResponse.success(user, "User retrieved successfully"));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_UPDATE') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable Long id, 
                                                @Valid @RequestBody UserRequest request) {
        UserResponse user = userService.updateUser(id, request);
        return ResponseEntity.ok(ApiResponse.success(user, "User updated successfully"));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_DELETE') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success(null, "User deleted successfully"));
    }
    
    @PostMapping("/{userId}/roles/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> assignRole(@PathVariable Long userId, @PathVariable Long roleId) {
        userService.assignRoleToUser(userId, roleId);
        return ResponseEntity.ok(ApiResponse.success(null, "Role assigned successfully"));
    }
    
    @DeleteMapping("/{userId}/roles/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> removeRole(@PathVariable Long userId, @PathVariable Long roleId) {
        userService.removeRoleFromUser(userId, roleId);
        return ResponseEntity.ok(ApiResponse.success(null, "Role removed successfully"));
    }
    
    @PostMapping("/{id}/enable")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> enableUser(@PathVariable Long id) {
        userService.enableUser(id);
        return ResponseEntity.ok(ApiResponse.success(null, "User enabled successfully"));
    }
    
    @PostMapping("/{id}/disable")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> disableUser(@PathVariable Long id) {
        userService.disableUser(id);
        return ResponseEntity.ok(ApiResponse.success(null, "User disabled successfully"));
    }
}