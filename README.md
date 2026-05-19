# Employee API Teaching Project

This project is a simple Spring Boot REST API built to teach JDBC-based layered architecture. It implements one operation end-to-end: `GET /employees/{id}`.

## Project structure

```text
com.example.employeeapi
  controller
    EmployeeController.java
  service
    EmployeeService.java
  dao
    EmployeeDao.java
  model
    Employee.java
  exception
    EmployeeNotFoundException.java
    GlobalExceptionHandler.java
```

- `controller`: receives HTTP requests and sends responses.
- `service`: contains the application flow between the controller and DAO.
- `dao`: runs SQL using `JdbcTemplate`.
- `model`: holds the `Employee` data object.
- `exception`: contains custom exception handling for clean API errors.

## Request flow for GET /employees/{id}

1. `EmployeeController` reads the employee ID from the URL path.
2. `EmployeeController` calls `EmployeeService`.
3. `EmployeeService` asks `EmployeeDao` for the employee.
4. `EmployeeDao` runs a SQL query with `JdbcTemplate` against the `EMPLOYEE` table.
5. If a row is found, it is mapped into an `Employee` object and returned as JSON.
6. If no row is found, `EmployeeNotFoundException` is thrown and `GlobalExceptionHandler` returns HTTP `404`.

## Sample curl command

```bash
curl http://localhost:8080/employees/1
```

If the employee does not exist, the API returns a JSON response like this:

```json
{
  "status": 404,
  "message": "Employee not found with id: 1"
}
```

## Current teaching scope

- `GET /employees/{id}` is fully implemented.
- `POST /employees` currently returns `501 Not Implemented`.
- `PATCH /employees/{id}` currently returns `501 Not Implemented`.
- `DELETE /employees/{id}` currently returns `501 Not Implemented`.

These placeholder endpoints are included so students can see the future API shape before implementing the remaining layers and operations.
