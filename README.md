# Spring Boot Postgres — API and DB Demo

A RESTful API built with **Spring Boot 3** and **PostgreSQL**.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17+ |
| Framework | Spring Boot 3.x |
| ORM | Spring Data JPA / Hibernate |
| Database | PostgreSQL |
| Build Tool | Maven |

---

## Prerequisites

- Java 17+
- Maven
- PostgreSQL running locally

---

## Database Setup

### 1. Start PostgreSQL and create the database

```bash
# Create your default postgres user database (Mac only, if not already done)
createdb ${your_mac_username}

# Connect to psql
psql

# Create the EHR database
CREATE DATABASE ${database_name};

# Confirm it was created
\l

# Exit
\q
```

### 2. Configure the connection

Create a `src/main/resources/application.properties` with properties below:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ehr_db
spring.datasource.username=your_mac_username
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

Or copy values from `src/main/resources/application-dev.properties` 

### How tables are created

This project uses **Hibernate's `ddl-auto=update`** setting. When the application starts, Hibernate automatically inspects the `@Entity` classes and creates or updates the corresponding PostgreSQL tables — no manual SQL required.

| `ddl-auto` value | Behaviour | When to use |
|---|---|---|
| `update` | Creates table if missing, adds new columns | Local development |
| `create` | Drops and recreates tables on every start | Early dev (data is lost) |
| `validate` | Checks schema matches model, errors if not | Pre-production |
| `none` | Does nothing — use migrations instead | Production |

> **Production note:** For production, replace `ddl-auto=update` with a migration tool like **Flyway** or **Liquibase** to manage schema changes safely with versioned SQL scripts.

### Verify tables were created

After starting the app, connect to psql and confirm:

```bash
psql -U ${your_mac_username -d} ${database_name}

# List all tables
\dt

# Inspect the patients table
\d patients

# Exit
\q
```

---

## Getting Started

### Clone and run

```bash
./mvnw spring-boot:run
```

The app starts at `http://localhost:8080`.

You will see Hibernate create the tables in the logs:

```
Hibernate: create table patients (id bigserial not null, ...)
Started SpringBootPostgresApplication in 3.2 seconds
```

---

## Project Structure

```
src/
└── main/
    └── java/
        └── com/example/springBootPostgres/
            │
            ├── SpringBootPostgresApplication.java   ← Entry point
            │
            ├── model/
            │   ├── **.java                          ← @Entity / DB table
            │   └── enums/
            │
            ├── repository/                          ← DB queries
            │   └── PatientRepository.java
            │
            ├── service/                             ← Business logic
            │
            └── controller/                          ← HTTP endpoints
```

---

## Architecture Pattern

This project follows the standard **Controller → Service → Repository** layered architecture:

```
HTTP Request
     │
     ▼
┌─────────────────────────────────────────────────────────┐
│  Controller  (@RestController)                          │
│  Handles HTTP requests and responses.                   │
│  No business logic — delegates everything to Service.   │
└───────────────────────┬─────────────────────────────────┘
                        │
                        ▼
┌─────────────────────────────────────────────────────────┐
│  Service  (@Service)                                    │
│  Contains all business logic.                           │
│  Validates data, enforces rules (e.g. no duplicate      │
│  emails, soft deletes only), calls Repository.          │
└───────────────────────┬─────────────────────────────────┘
                        │
                        ▼
┌─────────────────────────────────────────────────────────┐
│  Repository  (@Repository)                              │
│  Extends JpaRepository — handles all DB operations.     │
│  No logic here — only data access methods.              │
└───────────────────────┬─────────────────────────────────┘
                        │
                        ▼
                  PostgreSQL DB
```

### Layer responsibilities

| Layer | Annotation | Responsibility |
|---|---|---|
| **Controller** | `@RestController` | Receive HTTP requests, return HTTP responses |
| **Service** | `@Service` | Business rules, validation, orchestration |
| **Repository** | `@Repository` | Database queries via JPA / Hibernate |
| **Model** | `@Entity` | Represents a database table |


## Postman Collection

A Postman collection with all endpoints and example request bodies is included at:

```
patients.postman_collection.json
```

Import it into Postman via **Import → Upload File**.

---

Now you can test the API routes.