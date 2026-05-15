package com.rbac.config;

import com.rbac.entity.Permission;
import com.rbac.entity.Role;
import com.rbac.entity.User;
import com.rbac.repository.PermissionRepository;
import com.rbac.repository.RoleRepository;
import com.rbac.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PermissionRepository permissionRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        
        // Check if data already exists
        if (permissionRepository.count() > 0) {
            System.out.println("Data already initialized. Skipping...");
            return;
        }
        
        System.out.println("========================================");
        System.out.println("Initializing data...");
        System.out.println("========================================");
        
        // Create Permissions
        Permission userCreate = createPermission("USER_CREATE", "Permission to create users");
        Permission userRead = createPermission("USER_READ", "Permission to read users");
        Permission userUpdate = createPermission("USER_UPDATE", "Permission to update users");
        Permission userDelete = createPermission("USER_DELETE", "Permission to delete users");
        Permission roleCreate = createPermission("ROLE_CREATE", "Permission to create roles");
        Permission roleRead = createPermission("ROLE_READ", "Permission to read roles");
        Permission roleUpdate = createPermission("ROLE_UPDATE", "Permission to update roles");
        Permission roleDelete = createPermission("ROLE_DELETE", "Permission to delete roles");
        Permission permissionCreate = createPermission("PERMISSION_CREATE", "Permission to create permissions");
        Permission permissionRead = createPermission("PERMISSION_READ", "Permission to read permissions");
        Permission permissionUpdate = createPermission("PERMISSION_UPDATE", "Permission to update permissions");
        Permission permissionDelete = createPermission("PERMISSION_DELETE", "Permission to delete permissions");
        
        // Create Roles
        Role adminRole = createRole("ROLE_ADMIN", "Administrator with full access");
        Role managerRole = createRole("ROLE_MANAGER", "Manager with elevated access");
        Role userRole = createRole("ROLE_USER", "Regular user with basic access");
        
        // Assign Permissions to Admin Role (All permissions)
        adminRole.getPermissions().add(userCreate);
        adminRole.getPermissions().add(userRead);
        adminRole.getPermissions().add(userUpdate);
        adminRole.getPermissions().add(userDelete);
        adminRole.getPermissions().add(roleCreate);
        adminRole.getPermissions().add(roleRead);
        adminRole.getPermissions().add(roleUpdate);
        adminRole.getPermissions().add(roleDelete);
        adminRole.getPermissions().add(permissionCreate);
        adminRole.getPermissions().add(permissionRead);
        adminRole.getPermissions().add(permissionUpdate);
        adminRole.getPermissions().add(permissionDelete);
        
        // Assign Permissions to Manager Role
        managerRole.getPermissions().add(userRead);
        managerRole.getPermissions().add(userUpdate);
        managerRole.getPermissions().add(roleRead);
        managerRole.getPermissions().add(permissionRead);
        
        // Assign Permissions to User Role
        userRole.getPermissions().add(userRead);
        
        // Save roles with permissions
        roleRepository.save(adminRole);
        roleRepository.save(managerRole);
        roleRepository.save(userRole);
        
        // Create Users
        // Admin User (password: admin123)
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@rbac.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setFirstName("System");
        admin.setLastName("Admin");
        admin.setEnabled(true);
        admin.getRoles().add(adminRole);
        userRepository.save(admin);
        
        // Manager User (password: manager123)
        User manager = new User();
        manager.setUsername("manager");
        manager.setEmail("manager@rbac.com");
        manager.setPassword(passwordEncoder.encode("manager123"));
        manager.setFirstName("Jane");
        manager.setLastName("Manager");
        manager.setEnabled(true);
        manager.getRoles().add(managerRole);
        userRepository.save(manager);
        
        // Regular User (password: user123)
        User regularUser = new User();
        regularUser.setUsername("user");
        regularUser.setEmail("user@example.com");
        regularUser.setPassword(passwordEncoder.encode("user123"));
        regularUser.setFirstName("John");
        regularUser.setLastName("Doe");
        regularUser.setEnabled(true);
        regularUser.getRoles().add(userRole);
        userRepository.save(regularUser);
        
        System.out.println("Data initialization completed!");
        System.out.println("========================================");
        System.out.println("Default Credentials:");
        System.out.println("Admin    - username: admin,    password: admin123");
        System.out.println("Manager  - username: manager,  password: manager123");
        System.out.println("User     - username: user,     password: user123");
        System.out.println("========================================");
        System.out.println("Permissions Created: " + permissionRepository.count());
        System.out.println("Roles Created: " + roleRepository.count());
        System.out.println("Users Created: " + userRepository.count());
        System.out.println("========================================");
    }
    
    private Permission createPermission(String name, String description) {
        Permission permission = new Permission();
        permission.setName(name);
        permission.setDescription(description);
        return permissionRepository.save(permission);
    }
    
    private Role createRole(String name, String description) {
        Role role = new Role();
        role.setName(name);
        role.setDescription(description);
        return role;
    }
}