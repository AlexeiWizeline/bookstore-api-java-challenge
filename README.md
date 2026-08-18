````markdown
# Bookstore REST API

A sample Spring Boot REST API used for Java Backend Engineer technical interviews.

The project intentionally starts with a simple but production-like architecture that candidates will evolve through a series of user stories. The objective is to evaluate software design, Spring Boot knowledge, REST API design, persistence modeling, and testing practices rather than the ability to scaffold a project from scratch.

---

## Technology Stack

- Java 21
- Spring Boot 4.x
- Maven
- Spring Web MVC
- Spring Data JPA
- Hibernate
- H2 In-Memory Database
- Spring Validation
- OpenAPI / Swagger
- JUnit 5
- Mockito

---

## Project Structure

```
src
├── main
│   ├── java
│   │   └── com.wizeline.bookstore
│   │       ├── config
│   │       ├── controller
│   │       ├── dto
│   │       ├── entity
│   │       ├── exception
│   │       ├── mapper
│   │       ├── repository
│   │       ├── service
│   │       ├── util
│   │       └── validation
│   └── resources
│       ├── application.yml
│       └── data.sql
└── test
```

---

# Prerequisites

Before running the project ensure the following tools are installed:

- Java 21
- Maven 3.9+

Verify your installation:

```bash
java -version
```

Expected output:

```
openjdk version "21.x.x"
```

Verify Maven:

```bash
mvn -version
```

---

# Running the application

Clone the repository

```bash
git clone <repository-url>
cd bookstore-api
```

Compile the project

```bash
mvn clean install
```

Run the application

```bash
mvn spring-boot:run
```

The application starts on

```
http://localhost:8080
```

---

# Swagger UI

Once the application is running, the REST API can be explored through Swagger.

```
http://localhost:8080/swagger-ui/index.html
```

---

# H2 Database Console

The application uses an in-memory H2 database.

Console:

```
http://localhost:8080/h2-console
```

Connection settings

| Property | Value |
|----------|-------|
| JDBC URL | `jdbc:h2:mem:bookstore` |
| Username | `sa` |
| Password | *(empty)* |

---

# Running Tests

Run all tests

```bash
mvn test
```

Run a clean build with tests

```bash
mvn clean verify
```

---

# Existing REST Endpoints

| Method | Endpoint |
|---------|----------|
| GET | `/books` |
| GET | `/books/{id}` |
| POST | `/books` |
| PUT | `/books/{id}` |
| DELETE | `/books/{id}` |

---

# Interview User Stories

Candidates should implement the following user stories while preserving the existing functionality.

---

# User Story 1 — Unique ISBN

## Story

As a librarian,

I want every book to have a unique ISBN,

so duplicate books cannot be registered in the catalog.

---

## Functional Requirements

- ISBN must be unique.
- Creating a book with an existing ISBN must fail.
- Updating a book with an ISBN already assigned to another book must fail.
- Existing CRUD functionality must continue to work.

---

## Expected Behavior

Successful request

```
POST /books
```

Returns

```
201 Created
```

Duplicate ISBN

```
POST /books
```

Returns

```
409 Conflict
```

Example response

```json
{
    "timestamp": "2026-08-05T10:15:30",
    "status": 409,
    "error": "Duplicate ISBN",
    "message": "A book with ISBN 9780132350884 already exists."
}
```

---

## Evaluation Criteria

The interviewer will evaluate:

- REST status codes
- Validation strategy
- Business logic placement
- Persistence design
- Exception handling
- Database constraints
- Unit and integration tests
- Overall code quality

---

# User Story 2 — Search Books

## Story

As a librarian,

I want to search books using different criteria,

so I can quickly find books in the catalog.

---

## Functional Requirements

Enhance the existing

```
GET /books
```

endpoint to support filtering.

Supported query parameters:

| Parameter | Description |
|-----------|-------------|
| `title` | Partial match |
| `author` | Partial match |
| `publishedYear` | Exact year |
| `available` | Availability |

Examples

```
GET /books?author=Martin
```

```
GET /books?title=Spring
```

```
GET /books?available=true
```

```
GET /books?publishedYear=2021
```

Parameters should be combinable.

Example

```
GET /books?author=Martin&available=true
```

---

## Expected Behavior

When no filters are supplied

```
GET /books
```

returns every book.

When filters are supplied

only matching books should be returned.

If no books match

return

```
200 OK
```

with an empty array.

---

## Non-functional Requirements

The implementation should

- avoid unnecessary database queries
- keep controller logic minimal
- place business logic in the service layer
- leverage Spring Data JPA capabilities where appropriate
- remain easy to extend with future search filters

---

## Evaluation Criteria

The interviewer will evaluate:

- REST API design
- Spring MVC knowledge
- Repository design
- Hibernate/JPA usage
- Query implementation
- Layered architecture
- Maintainability
- Test coverage

---

# User Story 3 — Paginate and Sort Books

## Story

As a librarian,

I want to retrieve books in paginated pages and custom sort orders,

so the catalog loads quickly and efficiently without fetching unnecessary data.

---

## Functional Requirements

Enhance the existing

```
GET /books
```

endpoint to support pagination and sorting.

Supported query parameters:

| Parameter | Description | Default |
|-----------|-------------|---------|
| `page` | 0-based page index | `0` |
| `size` | Number of records per page | `10` |
| `sort` | Field name and direction (`field,asc` or `field,desc`) | `title,asc` |

Examples

```
GET /books?page=0&size=5
```

```
GET /books?page=1&size=10&sort=publishedYear,desc
```

```
GET /books?page=0&size=10&sort=author,asc
```

---

## Expected Behavior

The endpoint should return HTTP `200 OK` with a structured payload containing:

- The array of book records for the requested page.
- Metadata describing the current page number, page size, total pages, and total record count.

---

## Evaluation Criteria

The interviewer will evaluate:

- Use of framework pagination and sorting features
- API contract structure and metadata design
- Efficient database query execution (avoiding in-memory dataset slicing)
- Backwards compatibility with default parameters
- Unit test coverage

---

# General Expectations

Candidates are encouraged to:

- Write clean, readable code.
- Follow existing project conventions.
- Preserve backwards compatibility.
- Keep controllers thin.
- Place business logic in the service layer.
- Write or update automated tests.
- Consider maintainability over cleverness.

The goal of this exercise is not only to make the feature work, but to demonstrate good engineering practices and thoughtful software design.
````
