package com.rbac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RbacApplication {
    public static void main(String[] args) {
    	
        SpringApplication.run(RbacApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("=== RBAC System Started Successfully ===");
        System.out.println("========================================");
        System.out.println("H2 Console: http://localhost:8080/h2-console");
        System.out.println("H2 JDBC URL: jdbc:h2:mem:rbacdb");
        System.out.println("Username: sa");
        System.out.println("Password: (empty)");
        System.out.println("========================================");
        System.out.println("API Base URL: http://localhost:8080/api");
        System.out.println("========================================");
    }
}