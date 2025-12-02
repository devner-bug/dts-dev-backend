# dts-dev-backend

A task management app built for the **DTS Developer Technical Test - Junior Software Developer**. Helps caseworkers create daily tasks with a simple interface and reliable backend.

---

## Table of Contents

- [Backend Features](#backend-features)
- [Setup](#setup)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [Database Configuration](#database-configuration)

## Backend Features

- REST API for case management (Create operation only)
- H2 in-memory database (auto-creates tables via JPA)
- Data validation and error handling
- Unit/Integration tests (85% coverage)

---

## Setup

**Requirements:**

- Java 17+
- Maven

**Run the application:**

```bash
mvn spring-boot:run
```

**Access Points:**

- API:

[`http://localhost:8081/api/v1/task`](http://localhost:8081/api/v1/task)
- H2 Console:

[`http://localhost:8081/h2-console`](http://localhost:8081/h2-console)  
JDBC URL: `jdbc:h2:mem:testDB`  
*(Credentials in `application.yaml`)*

---

## API Documentation

Swagger UI:

[`http://localhost:8081/swagger-ui/index.html`](http://localhost:8081/swagger-ui/index.html)

| Method | Endpoint            | Description     |
|--------|---------------------|-----------------|
| POST   | `/api/v1/task`      | Create new Task |

---

## Testing

Run all tests:

```bash
mvn test
```

---

## ️Database Configuration

- H2 Console:

[`http://localhost:8081/h2-console`](http://localhost:8081/h2-console)
- JDBC URL: `jdbc:h2:mem:testDB`  
  *(Credentials match `.env` or `application.yaml`)*

---
