package com.example.employeeapi.dao;

import com.example.employeeapi.model.Employee;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDao {

    // The DAO talks directly to the database using JdbcTemplate.
    private final JdbcTemplate jdbcTemplate;

    public EmployeeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Employee findById(Long id) {
        String sql = """
                SELECT
                  ID,
                  DEPARTMENT_ID,
                  FIRST_NAME,
                  LAST_NAME,
                  EMAIL,
                  PHONE,
                  JOB_TITLE,
                  SALARY
                FROM EMPLOYEE
                WHERE ID = ?
                """;

        return jdbcTemplate.query(sql, employeeRowMapper(), id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    private RowMapper<Employee> employeeRowMapper() {
        return new RowMapper<Employee>() {
            @Override
            public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
                Employee employee = new Employee();
                employee.setId(rs.getLong("ID"));
                employee.setDepartmentId(getNullableLong(rs, "DEPARTMENT_ID"));
                employee.setFirstName(rs.getString("FIRST_NAME"));
                employee.setLastName(rs.getString("LAST_NAME"));
                employee.setEmail(rs.getString("EMAIL"));
                employee.setPhone(rs.getString("PHONE"));
                employee.setJobTitle(rs.getString("JOB_TITLE"));
                employee.setSalary(getNullableLong(rs, "SALARY"));
                return employee;
            }
        };
    }

    private Long getNullableLong(ResultSet rs, String columnName) throws SQLException {
        long value = rs.getLong(columnName);
        return rs.wasNull() ? null : value;
    }

    public Employee addEmployee(Employee employee) {
        String sql = """
                INSERT INTO EMPLOYEE(
                  DEPARTMENT_ID,
                  FIRST_NAME,
                  LAST_NAME,
                  EMAIL,
                  PHONE,
                  JOB_TITLE,
                  SALARY
                ) VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql, new String[]{"ID"});
            preparedStatement.setObject(1, employee.getDepartmentId());
            preparedStatement.setString(2, employee.getFirstName());
            preparedStatement.setString(3, employee.getLastName());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setString(5, employee.getPhone());
            preparedStatement.setString(6, employee.getJobTitle());
            preparedStatement.setObject(7, employee.getSalary());
            return preparedStatement;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            employee.setId(generatedId.longValue());
        }

        return employee;
    }

    public int deleteEmployee(Long id) {
        String sql = "DELETE FROM EMPLOYEE WHERE ID = ?";
        return jdbcTemplate.update(sql,id);
    }

    public Employee updateEmployee(Long id,Employee employee) {
        String sql = """
            UPDATE EMPLOYEE
            SET DEPARTMENT_ID = ?,
                FIRST_NAME = ?,
                LAST_NAME = ?,
                EMAIL = ?,
                PHONE = ?,
                JOB_TITLE = ?,
                SALARY = ?
            WHERE ID = ?
            """;
           int rowsDeleted =  jdbcTemplate.update(sql, 
            employee.getDepartmentId(),
            employee.getFirstName(),
            employee.getLastName(),
            employee.getEmail(),
            employee.getPhone(),
            employee.getJobTitle(),
            employee.getSalary(), id
            
    );
    if(rowsDeleted!=0){
        employee.setId(id);
    }
    else{
        return null;
    }
        return employee;
        

    }
}
