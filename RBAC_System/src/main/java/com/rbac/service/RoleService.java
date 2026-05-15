package com.rbac.service;

import com.rbac.dto.request.RoleRequest;
import com.rbac.dto.response.RoleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    RoleResponse createRole(RoleRequest request);
    RoleResponse updateRole(Long id, RoleRequest request);
    RoleResponse getRoleById(Long id);
    RoleResponse getRoleByName(String name);
    Page<RoleResponse> getAllRoles(Pageable pageable);
    void deleteRole(Long id);
    void assignPermissionToRole(Long roleId, Long permissionId);
    void removePermissionFromRole(Long roleId, Long permissionId);
}