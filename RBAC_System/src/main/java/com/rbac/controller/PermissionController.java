package com.rbac.controller;

import com.rbac.dto.request.PermissionRequest;
import com.rbac.dto.response.ApiResponse;
import com.rbac.dto.response.PermissionResponse;
import com.rbac.service.PermissionService;
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
@RequestMapping("/api/permissions")
public class PermissionController {
    
    @Autowired
    private PermissionService permissionService;
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PermissionResponse>> createPermission(@Valid @RequestBody PermissionRequest request) {
        PermissionResponse permission = permissionService.createPermission(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(permission, "Permission created successfully"));
    }
    
    @GetMapping
    @PreAuthorize("hasAuthority('PERMISSION_READ') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<PermissionResponse>>> getAllPermissions(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PermissionResponse> permissions = permissionService.getAllPermissions(pageable);
        return ResponseEntity.ok(ApiResponse.success(permissions, "Permissions retrieved successfully"));
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PERMISSION_READ') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PermissionResponse>> getPermissionById(@PathVariable Long id) {
        PermissionResponse permission = permissionService.getPermissionById(id);
        return ResponseEntity.ok(ApiResponse.success(permission, "Permission retrieved successfully"));
    }
    
    @GetMapping("/name/{name}")
    @PreAuthorize("hasAuthority('PERMISSION_READ') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PermissionResponse>> getPermissionByName(@PathVariable String name) {
        PermissionResponse permission = permissionService.getPermissionByName(name);
        return ResponseEntity.ok(ApiResponse.success(permission, "Permission retrieved successfully"));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PermissionResponse>> updatePermission(
            @PathVariable Long id, 
            @Valid @RequestBody PermissionRequest request) {
        PermissionResponse permission = permissionService.updatePermission(id, request);
        return ResponseEntity.ok(ApiResponse.success(permission, "Permission updated successfully"));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Permission deleted successfully"));
    }
}