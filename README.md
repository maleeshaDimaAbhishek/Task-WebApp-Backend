# Task Management Backend

## Overview

This is the backend service for the Task Management System built using Spring Boot.

## Technologies Used

* Java 21
* Spring Boot
* Spring Security
* JWT Authentication
* Spring Data JPA
* MySQL
* Maven

## How to Run Backend

### 1. Clone Repository

git clone <your-backend-repository-url>

### 2. Open Project

Import into IntelliJ IDEA or any Java IDE.

### 3. Configure Database

Update `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/task_db
spring.datasource.username=username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 4. Run Project

Run main application class:

TaskManagementApplication.java

Or using terminal:

```bash
mvn spring-boot:run
```

Backend runs on:

http://localhost:8080

## JWT Authentication

After login, JWT token is returned.

Use token in requests:

Authorization: Bearer <token>

## Default Credentials

Admin Login:

username: admin
password: admin123

## API Base URL

http://localhost:8080/api

## Database Setup

Create PostgreSQL database:

```sql
CREATE DATABASE task_management;
```

## Important Notes

* JWT secret key must be minimum 256 bits
* CORS configured for Angular frontend
