package com.rbac.service;

import com.rbac.dto.request.PermissionRequest;
import com.rbac.dto.response.PermissionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PermissionService {
    PermissionResponse createPermission(PermissionRequest request);
    PermissionResponse updatePermission(Long id, PermissionRequest request);
    PermissionResponse getPermissionById(Long id);
    PermissionResponse getPermissionByName(String name);
    Page<PermissionResponse> getAllPermissions(Pageable pageable);
    void deletePermission(Long id);
}