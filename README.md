# RBAC System - Role Based Access Control

A production-ready Role-Based Access Control (RBAC) system built with Spring Boot 3, Spring Security, JWT, and H2 Database. This system provides complete user management, role management, and permission management with secure JWT authentication.

## 📋 Table of Contents
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Database Schema](#database-schema)
- [API Documentation](#api-documentation)
- [Default Credentials](#default-credentials)
- [Testing with Postman](#testing-with-postman)
- [H2 Database Console](#h2-database-console)
- [Security Configuration](#security-configuration)
- [Error Handling](#error-handling)
- [Contributing](#contributing)
- [License](#license)

## ✨ Features

- ✅ **JWT Authentication** - Secure token-based authentication
- ✅ **Role-Based Access Control** - Fine-grained access control with roles and permissions
- ✅ **User Management** - Complete CRUD operations for users
- ✅ **Role Management** - Create, update, delete roles
- ✅ **Permission Management** - Manage permissions at granular level
- ✅ **Secure APIs** - All APIs protected with Spring Security
- ✅ **Layered Architecture** - Controller, Service, Repository pattern
- ✅ **Exception Handling** - Global exception handling with meaningful messages
- ✅ **Request Validation** - Input validation using Jakarta Validation
- ✅ **Pagination Support** - Paginated responses for list endpoints
- ✅ **H2 Database** - Embedded database for development
- ✅ **Method-Level Security** - Pre/Post authorization annotations

## 🛠 Technology Stack

| Technology | Version |
|------------|---------|
| Java | 17+ |
| Spring Boot | 3.1.5 |
| Spring Security | 6.1.5 |
| Spring Data JPA | 3.1.5 |
| JWT (JJWT) | 0.12.3 |
| H2 Database | 2.1.214 |
| Maven | 3.8+ |
| Lombok | Latest |

## 📦 Prerequisites

Before you begin, ensure you have the following installed:

- Java 17 or higher
- Maven 3.8+
- Git
- Postman (for API testing)
- Your favorite IDE (IntelliJ IDEA, Eclipse, VS Code)

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/rbac-system.git
cd rbac-system
