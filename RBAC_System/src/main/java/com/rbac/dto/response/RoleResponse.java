package com.rbac.dto.response;

import com.rbac.entity.Permission;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {
    private Long id;
    private String name;
    private String description;
    private Set<Permission> permissions;
}