package com.rbac.service;

import com.rbac.dto.request.RoleRequest;
import com.rbac.dto.response.RoleResponse;
import com.rbac.entity.Permission;
import com.rbac.entity.Role;
import com.rbac.exception.DuplicateResourceException;
import com.rbac.exception.ResourceNotFoundException;
import com.rbac.repository.PermissionRepository;
import com.rbac.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PermissionRepository permissionRepository;
    
    @Override
    public RoleResponse createRole(RoleRequest request) {
        if (roleRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Role already exists with name: " + request.getName());
        }
        
        Role role = new Role();
        role.setName(request.getName());
        role.setDescription(request.getDescription());
        
        Role savedRole = roleRepository.save(role);
        return convertToResponse(savedRole);
    }
    
    @Override
    public RoleResponse updateRole(Long id, RoleRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
        
        role.setName(request.getName());
        role.setDescription(request.getDescription());
        
        Role updatedRole = roleRepository.save(role);
        return convertToResponse(updatedRole);
    }
    
    @Override
    public RoleResponse getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
        return convertToResponse(role);
    }
    
    @Override
    public RoleResponse getRoleByName(String name) {
        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + name));
        return convertToResponse(role);
    }
    
    @Override
    public Page<RoleResponse> getAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable).map(this::convertToResponse);
    }
    
    @Override
    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Role not found with id: " + id);
        }
        roleRepository.deleteById(id);
    }
    
    @Override
    public void assignPermissionToRole(Long roleId, Long permissionId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found"));
        
        role.getPermissions().add(permission);
        roleRepository.save(role);
    }
    
    @Override
    public void removePermissionFromRole(Long roleId, Long permissionId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found"));
        
        role.getPermissions().remove(permission);
        roleRepository.save(role);
    }
    
    private RoleResponse convertToResponse(Role role) {
        RoleResponse response = new RoleResponse();
        response.setId(role.getId());
        response.setName(role.getName());
        response.setDescription(role.getDescription());
        response.setPermissions(role.getPermissions());
        return response;
    }
}