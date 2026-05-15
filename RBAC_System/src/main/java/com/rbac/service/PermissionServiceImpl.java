package com.rbac.service;

import com.rbac.dto.request.PermissionRequest;
import com.rbac.dto.response.PermissionResponse;
import com.rbac.entity.Permission;
import com.rbac.exception.DuplicateResourceException;
import com.rbac.exception.ResourceNotFoundException;
import com.rbac.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    
    @Autowired
    private PermissionRepository permissionRepository;
    
    @Override
    public PermissionResponse createPermission(PermissionRequest request) {
        if (permissionRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Permission already exists with name: " + request.getName());
        }
        
        Permission permission = new Permission();
        permission.setName(request.getName());
        permission.setDescription(request.getDescription());
        
        Permission savedPermission = permissionRepository.save(permission);
        return convertToResponse(savedPermission);
    }
    
    @Override
    public PermissionResponse updatePermission(Long id, PermissionRequest request) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id: " + id));
        
        permission.setName(request.getName());
        permission.setDescription(request.getDescription());
        
        Permission updatedPermission = permissionRepository.save(permission);
        return convertToResponse(updatedPermission);
    }
    
    @Override
    public PermissionResponse getPermissionById(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id: " + id));
        return convertToResponse(permission);
    }
    
    @Override
    public PermissionResponse getPermissionByName(String name) {
        Permission permission = permissionRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with name: " + name));
        return convertToResponse(permission);
    }
    
    @Override
    public Page<PermissionResponse> getAllPermissions(Pageable pageable) {
        return permissionRepository.findAll(pageable).map(this::convertToResponse);
    }
    
    @Override
    public void deletePermission(Long id) {
        if (!permissionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Permission not found with id: " + id);
        }
        permissionRepository.deleteById(id);
    }
    
    private PermissionResponse convertToResponse(Permission permission) {
        PermissionResponse response = new PermissionResponse();
        response.setId(permission.getId());
        response.setName(permission.getName());
        response.setDescription(permission.getDescription());
        return response;
    }
}