package com.rbac.controller;

import com.rbac.dto.request.RoleRequest;
import com.rbac.dto.response.ApiResponse;
import com.rbac.dto.response.RoleResponse;
import com.rbac.service.RoleService;
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
@RequestMapping("/api/roles")
public class RoleController {
    
    @Autowired
    private RoleService roleService;
    
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CREATE') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(@Valid @RequestBody RoleRequest request) {
        RoleResponse role = roleService.createRole(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(role, "Role created successfully"));
    }
    
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_READ') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<RoleResponse>>> getAllRoles(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<RoleResponse> roles = roleService.getAllRoles(pageable);
        return ResponseEntity.ok(ApiResponse.success(roles, "Roles retrieved successfully"));
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_READ') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<RoleResponse>> getRoleById(@PathVariable Long id) {
        RoleResponse role = roleService.getRoleById(id);
        return ResponseEntity.ok(ApiResponse.success(role, "Role retrieved successfully"));
    }
    
    @GetMapping("/name/{name}")
    @PreAuthorize("hasAuthority('ROLE_READ') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<RoleResponse>> getRoleByName(@PathVariable String name) {
        RoleResponse role = roleService.getRoleByName(name);
        return ResponseEntity.ok(ApiResponse.success(role, "Role retrieved successfully"));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_UPDATE') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<RoleResponse>> updateRole(@PathVariable Long id, 
                                                @Valid @RequestBody RoleRequest request) {
        RoleResponse role = roleService.updateRole(id, request);
        return ResponseEntity.ok(ApiResponse.success(role, "Role updated successfully"));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_DELETE') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Role deleted successfully"));
    }
    
    @PostMapping("/{roleId}/permissions/{permissionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> assignPermission(@PathVariable Long roleId, 
                                              @PathVariable Long permissionId) {
        roleService.assignPermissionToRole(roleId, permissionId);
        return ResponseEntity.ok(ApiResponse.success(null, "Permission assigned to role successfully"));
    }
    
    @DeleteMapping("/{roleId}/permissions/{permissionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> removePermission(@PathVariable Long roleId, 
                                             @PathVariable Long permissionId) {
        roleService.removePermissionFromRole(roleId, permissionId);
        return ResponseEntity.ok(ApiResponse.success(null, "Permission removed from role successfully"));
    }
}